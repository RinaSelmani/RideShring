package com.thesis.ridesharing.ui.home.map

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.models.RidesPinEvent
import com.thesis.ridesharing.ui.rides.add_ride.AddRideActivity
import com.thesis.ridesharing.ui.rides.find_ride.FindRideActivity
import org.greenrobot.eventbus.EventBus

class MapModel {
    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    val TAG = "ERROR IN RIDES"

    init {
        firestoreDb = FirebaseFirestore.getInstance()
        getRides()
    }


    fun findRide() {
        EventBus.getDefault().post(OpenActivityEvent(FindRideActivity()))
    }

    fun addRide() {
        EventBus.getDefault().post(OpenActivityEvent(AddRideActivity()))

    }

    fun getRides() {
        val rides = mutableListOf<Ride>()
        firestoreDb.collection(RIDE_COLLECTION).orderBy("miliseconds").limit(10).get()
            .addOnSuccessListener {
                for (doc in it.documents) {
                    val ride = doc.toObject(Ride::class.java)!!
                    ride.id = doc.id
                    rides.add(ride)
                }
            }
            .addOnCompleteListener {
                EventBus.getDefault().post(RidesPinEvent(rides))

            }
            .addOnFailureListener() {
                Log.d(TAG, it.localizedMessage!!.toString())
            }
    }


}