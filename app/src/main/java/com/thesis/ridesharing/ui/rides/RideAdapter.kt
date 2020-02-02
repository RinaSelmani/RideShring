package com.thesis.ridesharing.ui.rides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RideItemBinding
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.CancelReservationEvent
import com.thesis.ridesharing.models.DeleteRideEvent
import com.thesis.ridesharing.models.ReserveRideEvent
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.ui.rides.ride_detail.RideDetailActivity
import org.greenrobot.eventbus.EventBus

class RideAdapter : RecyclerView.Adapter<RideAdapter.RideItemViewModel>() {
    lateinit var rides: MutableList<Ride>
    var isSearchRide = false
    var isParticipated = false
    var isDelete = false
    var currentUserId = ""
    lateinit var binding: RideItemBinding
    override fun onCreateViewHolder(container: ViewGroup, position: Int): RideItemViewModel {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.ride_item, container, false
        )
        binding.model = RideItemViewModel(binding)
        return RideItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return rides.size
    }

    fun updateTextAndNumberOfSeats(rideId: String, numberOfSeats: Int, textOfButton: String) {
        for (r in rides) {
            if (r.id == rideId) {
                if (textOfButton.equals("Reserve")) {
                    r.passengers.remove(currentUserId)
                } else {
                    r.passengers.add(currentUserId)
                }
                r.freeSeats = numberOfSeats

                break
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RideItemViewModel, position: Int) {
        holder.binding.position = position
        holder.binding.ride = rides[position]
        if (isSearchRide and !rides[position].riderId.equals(currentUserId)) {
            holder.binding.reserveButton.visibility = View.VISIBLE
            if (currentUserId in rides[position].passengers) {
                holder.binding.reserveButton.setText("Cancel Reservation")
            } else {
                holder.binding.reserveButton.setText("Reserve")

            }
        }
        if (isParticipated) {
            holder.binding.reserveButton.visibility = View.VISIBLE
            holder.binding.reserveButton.setText("Cancel Reservation")


        }
        if (isDelete) {
            holder.binding.deleteImagebutton.visibility = View.VISIBLE
        }
    }

    fun setRidesList(rides: MutableList<Ride>) {
        this.rides = rides
        notifyDataSetChanged()
    }

    inner class RideItemViewModel(val binding: RideItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun openRideDetails(position: Int) {
            EventBus.getDefault()
                .post(OpenActivityEvent(RideDetailActivity(), objToPass = rides[position].id))
        }

        fun deleteRide(position: Int){
            val rideID=rides[position].id
            rides.removeAt(position)
            EventBus.getDefault().post(DeleteRideEvent(rideID))
            notifyDataSetChanged()

        }

        fun onReservationButtonClicked(position: Int) {
            if (isSearchRide) {
                reserve(position)
            } else {
                val rideId = rides[position].id
                val numberOfFreeSeats = rides[position].freeSeats
                val passangers = rides[position].passengers
                passangers.remove(currentUserId)
                EventBus.getDefault().post(
                    CancelReservationEvent(
                        rideId = rideId,
                        numberOfFreeSeats = numberOfFreeSeats,
                        passangers = passangers
                    )
                )
                rides.removeAt(position)

                notifyDataSetChanged()


            }
        }

        fun reserve(position: Int) {
            val text = binding.reserveButton.text.toString()
            val rideId = rides[position].id
            val numberOfFreeSeats = rides[position].freeSeats
            val numberOfSeats = (rides[position].vehicle.numberOfSeats - 1).toString()
            val passangers = rides[position].passengers
            if (text.equals("Reserve")) {
                binding.reserveButton.setText("Cancel Reservation")
                val numberOfFreeSeatsString=(numberOfFreeSeats - 1).toString()
                rides[position].freeSeats = numberOfFreeSeats - 1
                binding.numberOfFreeSeats.setText("$numberOfFreeSeatsString/$numberOfSeats")
                passangers.add(currentUserId)
                EventBus.getDefault().post(
                    ReserveRideEvent(
                        rideId = rideId,
                        numberOfFreeSeats = numberOfFreeSeats,
                        passangers = passangers
                    )
                )

            } else {
                binding.reserveButton.setText("Reserve")
                val numberOfFreeSeatsString=(numberOfFreeSeats + 1).toString()
                rides[position].freeSeats = numberOfFreeSeats + 1
                binding.numberOfFreeSeats.setText("$numberOfFreeSeatsString/$numberOfSeats")
                passangers.remove(currentUserId)
                EventBus.getDefault().post(
                    CancelReservationEvent(
                        rideId = rideId,
                        numberOfFreeSeats = numberOfFreeSeats,
                        passangers = passangers
                    )
                )

            }


        }

    }
}