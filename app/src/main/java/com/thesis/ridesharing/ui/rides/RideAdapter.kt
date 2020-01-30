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
import com.thesis.ridesharing.models.ReserveRideEvent
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.ui.rides.ride_detail.RideDetailActivity
import org.greenrobot.eventbus.EventBus

class RideAdapter : RecyclerView.Adapter<RideAdapter.RideItemViewModel>() {
    lateinit var rides: MutableList<Ride>
    var isSearchRide = false
    var currentUserId = ""
    override fun onCreateViewHolder(container: ViewGroup, position: Int): RideItemViewModel {
        val binding: RideItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.ride_item, container, false
        )
        binding.model = RideItemViewModel(binding)
        return RideItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return rides.size
    }

    override fun onBindViewHolder(holder: RideItemViewModel, position: Int) {
        holder.binding.position = position
        holder.binding.ride = rides[position]
        if (isSearchRide and !rides[position].riderId.equals(currentUserId)) {
            holder.binding.reserveButton.visibility = View.VISIBLE
            if (currentUserId in rides[position].passengers) {
                holder.binding.reserveButton.setText("Cancel Reservation")
            }
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

        fun reserve(position: Int) {
            val text = binding.reserveButton.text.toString()
            val rideId = rides[position].id
            val numberOfFreeSeats = rides[position].freeSeats
            val passangers = rides[position].passengers
            if (text.equals("Reserve")) {
                binding.reserveButton.setText("Cancel Reservation")
                binding.numberOfFreeSeats.setText((numberOfFreeSeats - 1).toString())
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
                binding.numberOfFreeSeats.setText((numberOfFreeSeats + 1).toString())
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