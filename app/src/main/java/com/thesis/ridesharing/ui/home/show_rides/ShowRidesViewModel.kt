package com.thesis.ridesharing.ui.home.show_rides

import android.util.Log
import android.widget.Toast
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

    fun cancelRide(
        rideId: String,
        numberOfFreeSeats: Int,
        passangers: MutableList<String>
    ) {
        val updateHashMap = HashMap<String, Any>()
        updateHashMap["freeSeats"] = numberOfFreeSeats + 1
        updateHashMap["passengers"] = passangers
        firestoreDb.collection(RIDE_COLLECTION).document(rideId)
            .update(updateHashMap).addOnSuccessListener {
                Log.d("UPDATERIDE", "GOOD")

            }
            .addOnFailureListener {
                Log.d("UPDATERIDE", "BADD ${it.localizedMessage}")
            }


    }

    fun deleteRide(rideId:String){
        firestoreDb.collection(RIDE_COLLECTION).document(rideId).delete().addOnSuccessListener {
            Toast.makeText(binding.root.context,"Ride deleted succefully",Toast.LENGTH_SHORT).show()
        }
    }


    private fun getMyArchivedRides() {
        firestoreDb.collection(RIDE_COLLECTION)
            .whereLessThan("miliseconds", System.currentTimeMillis()).get()
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
        firestoreDb.collection(RIDE_COLLECTION)
            .whereGreaterThanOrEqualTo("miliseconds", System.currentTimeMillis()).get()
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
                adapter.isDelete=true
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
                adapter.isParticipated=true
                adapter.currentUserId=uid
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }

    }
}