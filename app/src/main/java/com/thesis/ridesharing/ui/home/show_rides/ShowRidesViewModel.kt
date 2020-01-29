package com.thesis.ridesharing.ui.home.show_rides

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.RidesListFragmentBinding
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.ui.rides.RideAdapter
import java.util.*

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
            "archivedPostedByMe" -> {
                getMyArchivedRides()

            }
            "participated" -> {
                getParticipatedRides()
            }
            "archivedParticipated" -> {
                myArchivedParticipatedRides()

            }
        }
    }

    private fun getMyArchivedRides() {
        val time = Date()
        firestoreDb.collection(RIDE_COLLECTION).whereLessThan("dateTime", time).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    if (d.exists() and (d["riderId"].toString() == uid)) {
                        val id = d.id
                        val ride: Ride? = d.toObject(Ride::class.java)
                        ride!!.id = id
                        listOfRides.add(ride)

                    }

                }
                Log.d("SIZEOF LIST", listOfRides.size.toString())
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter
                Log.d("SIZE", listOfRides.size.toString())

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }
    }

    private fun myArchivedParticipatedRides() {
        val time = Date()
        firestoreDb.collection(RIDE_COLLECTION).whereLessThan("dateTime", time).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    if (d.exists()) {
                        val passenger: MutableList<String> = d["passengers"] as MutableList<String>
                        if (uid in passenger) {
                            val id = d.id
                            val ride: Ride? = d.toObject(Ride::class.java)
                            ride!!.id = id
                            listOfRides.add(ride)

                        }

                    }

                }
                Log.d("SIZEOF LIST", listOfRides.size.toString())
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter
                Log.d("SIZE", listOfRides.size.toString())

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }

    }

    private fun getMyRides() {
        val time = Date()
        firestoreDb.collection(RIDE_COLLECTION).whereGreaterThanOrEqualTo("dateTime", time).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    if (d.exists() and (d["riderId"].toString() == uid)) {
                        val id = d.id
                        val ride: Ride? = d.toObject(Ride::class.java)
                        ride!!.id = id
                        listOfRides.add(ride)

                    }

                }
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }
    }

    fun getParticipatedRides() {
        val time = Date()
        firestoreDb.collection(RIDE_COLLECTION).whereGreaterThanOrEqualTo("dateTime", time).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    val passenger: MutableList<String> = d["passengers"] as MutableList<String>
                    if (d.exists()) {
                        if (uid in passenger) {
                            val id = d.id
                            val ride: Ride? = d.toObject(Ride::class.java)
                            ride!!.id = id
                            listOfRides.add(ride)

                        }
                    }

                }
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }

    }
}