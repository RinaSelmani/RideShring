package com.thesis.ridesharing.ui.home.show_rides

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.RidesListFragmentBinding
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.ui.rides.RideAdapter
import java.text.SimpleDateFormat

class ShowRidesViewModel(
    val binding: RidesListFragmentBinding,
    val adapter: RideAdapter,
    val type: String
) {

    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    var uid: String


    init {
        firestoreDb = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        when (type) {
            "postedByMe" -> {
                getMyRides()
            }
        }
    }

    private fun getMyRides() {
        firestoreDb.collection(RIDE_COLLECTION).whereEqualTo("riderId", uid).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    if (d.exists()) {
                        val id = d.id
                        val ride: Ride? = d.toObject(Ride::class.java)
                        ride!!.id = id
                        listOfRides.add(ride)

                        Log.d("DATETIME", ride.dateTime.toString())
                        firestoreDb.collection("USERS").document(uid).get().addOnSuccessListener {
                            if (it != null) {
                                val user = it.toObject(User::class.java)
                                ride.riderProfile = user!!

                            }
                        }
                    }

                }
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter
                Log.d("SIZE", listOfRides.size.toString())

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }
    }
}