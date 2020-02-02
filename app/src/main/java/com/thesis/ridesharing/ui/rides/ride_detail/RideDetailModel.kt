package com.thesis.ridesharing.ui.rides.ride_detail

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.RideDetailsActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.ChangeReservationEvent
import com.thesis.ridesharing.models.Ride
import org.greenrobot.eventbus.EventBus


class RideDetailModel(val binding: RideDetailsActivityBinding, val rideId: String) {
    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    var uid: String
    var rideObj: Ride? = Ride()

    init {
        firestoreDb = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        getRide(rideId)
    }


    fun getRide(id: String) {
        firestoreDb.collection(RIDE_COLLECTION).document(id).get().addOnSuccessListener {
            val ride = it.toObject(Ride::class.java)
            ride!!.id = it.id
            rideObj = ride
            binding.ride = ride
            if (!ride!!.riderId.equals(uid)) {
                binding.reserveButton.visibility = View.VISIBLE
                if (uid in ride.passengers) {
                    binding.reserveButton.setText("Cancel Reservation")
                } else {
                    binding.reserveButton.setText("Reserve")
                }
            }


        }
            .addOnFailureListener {
                Log.d("ERROR", it.toString())
            }
    }


    fun reserve() {
        val text = binding.reserveButton.text.toString()
        val rideId = rideObj!!.id
        val numberOfFreeSeats = rideObj!!.freeSeats
        val numberOfSeats = (rideObj!!.vehicle.numberOfSeats - 1).toString()
        val passangers = rideObj!!.passengers
        if (text.equals("Reserve")) {
            binding.reserveButton.setText("Cancel Reservation")
            val numberOfFreeSeatsString = (numberOfFreeSeats - 1).toString()
            rideObj!!.freeSeats = numberOfFreeSeats - 1
            binding.numberOfFreeSeats.setText("$numberOfFreeSeatsString/$numberOfSeats")
            passangers.add(uid)
            reserveRide(rideId, numberOfFreeSeats - 1, passangers)


        } else {
            binding.reserveButton.setText("Reserve")
            val numberOfFreeSeatsString = (numberOfFreeSeats + 1).toString()
            rideObj!!.freeSeats = numberOfFreeSeats + 1
            binding.numberOfFreeSeats.setText("$numberOfFreeSeatsString/$numberOfSeats")
            passangers.remove(uid)
            cancelRide(rideId, numberOfFreeSeats + 1, passangers)


        }

    }

    fun changeReservationsAndClose(text: String) {
        EventBus.getDefault().post(ChangeReservationEvent(text))

    }

    fun reserveRide(
        rideId: String,
        numberOfFreeSeats: Int,
        passangers: MutableList<String>
    ) {
        val updateHashMap = HashMap<String, Any>()
        updateHashMap["freeSeats"] = numberOfFreeSeats
        updateHashMap["passengers"] = passangers
        firestoreDb.collection(RIDE_COLLECTION).document(rideId)
            .update(updateHashMap).addOnSuccessListener {
                Log.d("UPDATERIDE", "GOOD")
                EventBus.getDefault()
                    .post(ChangeReservationEvent("$rideId $numberOfFreeSeats Cancel Reservation"))

            }
            .addOnFailureListener {
                Log.d("UPDATERIDE", "BADD ${it.localizedMessage}")
                Toast.makeText(
                    binding.root.context,
                    "Something went wrong please close the screen and open it again",
                    Toast.LENGTH_SHORT
                ).show()

            }


    }


    fun cancelRide(
        rideId: String,
        numberOfFreeSeats: Int,
        passangers: MutableList<String>
    ) {
        val updateHashMap = HashMap<String, Any>()
        updateHashMap["freeSeats"] = numberOfFreeSeats
        updateHashMap["passengers"] = passangers
        firestoreDb.collection(RIDE_COLLECTION).document(rideId)
            .update(updateHashMap).addOnSuccessListener {
                Log.d("UPDATERIDE", "GOOD")
                EventBus.getDefault()
                    .post(ChangeReservationEvent("$rideId $numberOfFreeSeats Reserve"))


            }
            .addOnFailureListener {
                Log.d("UPDATERIDE", "BADD ${it.localizedMessage}")
                Toast.makeText(
                    binding.root.context,
                    "Something went wrong please close the screen and open it again",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }
}