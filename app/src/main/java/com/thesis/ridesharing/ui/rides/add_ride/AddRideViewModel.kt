package com.thesis.ridesharing.ui.rides.add_ride

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.AddRideActivityBinding
import com.thesis.ridesharing.models.Vehicle
import com.thesis.ridesharing.ui.my_vehicles.MyVehiclesAdapter

class AddRideViewModel(val binding: AddRideActivityBinding, val adapter: MyVehiclesAdapter) {

    private var firestoreDb: FirebaseFirestore
    val VEHICLE_COLLECTION = "Vehicles"
    var uid: String
    val vehicles: MutableList<Vehicle>

    init {
        vehicles = mutableListOf()
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        binding.myVehiclesRecycleView.adapter = adapter
        firestoreDb = FirebaseFirestore.getInstance()
        getVehicles()
    }


    fun getVehicles() {
        vehicles.clear()
        adapter.setListOfVehicles(mutableListOf())
        //binding.progressBarHolder.visibility = View.VISIBLE
        firestoreDb.collection(VEHICLE_COLLECTION).document(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val res = it.get("VehicleArray")!!
                val test = res as ArrayList<HashMap<String, String>>

                for (i in test) {
                    val vehicle = Vehicle(
                        brand = i["brand"].toString(),
                        model = i["model"].toString(),
                        color = i["color"].toString(),
                        yearOfProduction = i["yearOfProduction"].toString(),
                        numberOfSeats = i["numberOfSeats"].toString().toInt()
                    )
                    vehicles.add(vehicle)
                }

                adapter.setListOfVehicles(vehicles)
                binding.myVehiclesRecycleView.adapter = adapter


            }
        }
            .addOnFailureListener {
                Toast.makeText(binding.root.context, "An error has occured", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ERROR", it.localizedMessage.toString())
            }

        Handler().postDelayed({

            binding.progressBarHolder.visibility = View.INVISIBLE


        }, 3000)


    }
}