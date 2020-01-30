package com.thesis.ridesharing.ui.rides.ride_detail

import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.RideDetailsActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.Ride
import org.greenrobot.eventbus.EventBus

class RideDetailModel(val binding: RideDetailsActivityBinding, val rideId: String) {
    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"

    init {
        firestoreDb = FirebaseFirestore.getInstance()
        getRide(rideId)
    }


    fun getRide(id: String) {
        firestoreDb.collection(RIDE_COLLECTION).document(id).get().addOnSuccessListener {
            val ride = it.toObject(Ride::class.java)
            binding.ride = ride
            if (!ride?.rideDescription.equals("")){
                binding.descriptionContainer.visibility= View.VISIBLE
            }

        }
            .addOnFailureListener {
                Log.d("ERROR", it.toString())
            }
    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }
}