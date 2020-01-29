package com.thesis.ridesharing.ui.my_vehicles

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.MyVehiclesActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.Vehicle
import com.thesis.ridesharing.ui.add_vehicle.AddVehicleActivity
import org.greenrobot.eventbus.EventBus

class MyVehiclesModel(val binding: MyVehiclesActivityBinding, val adapter: MyVehiclesAdapter) {
    private var firestoreDb: FirebaseFirestore
    val VEHICLE_COLLECTION = "Vehicles"
    var uid: String

    init {

        uid = FirebaseAuth.getInstance().currentUser!!.uid
        binding.vehiclesRecycleView.adapter = adapter
        firestoreDb = FirebaseFirestore.getInstance()
    }


    fun getVehicles() {
        val myVehicles = mutableListOf<Vehicle>()
        adapter.setListOfVehicles(mutableListOf())
        binding.progressBarHolder.visibility = View.VISIBLE
        firestoreDb.collection(VEHICLE_COLLECTION).document(uid).collection(uid).get()
            .addOnSuccessListener {

                for (i in it.documents) {
                    val vehicle = i.toObject(Vehicle::class.java)
                    vehicle?.vehicleId = i.id
                    myVehicles.add(vehicle!!)
                }

                adapter.setListOfVehicles(myVehicles)
                binding.vehiclesRecycleView.removeAllViews()
                binding.vehiclesRecycleView.adapter = adapter


            }
            .addOnFailureListener {
                Toast.makeText(binding.root.context, "An error has occured", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ERROR", it.localizedMessage.toString())
            }

        binding.progressBarHolder.visibility = View.INVISIBLE


    }

    fun deleteVehicle(position: Int) {
        val vehicleId = adapter.vehicles[position].vehicleId
        firestoreDb.collection(VEHICLE_COLLECTION).document(uid).collection(uid).document(vehicleId)
            .delete().addOnSuccessListener {

                Toast.makeText(
                    binding.root.context,
                    "Vehicle deleted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    binding.root.context,
                    "Vehicle could not be delete, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        binding.progressBarHolder.visibility = View.INVISIBLE


    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun openAddCarActivity() {
        EventBus.getDefault().post(OpenActivityEvent(AddVehicleActivity()))
    }
}