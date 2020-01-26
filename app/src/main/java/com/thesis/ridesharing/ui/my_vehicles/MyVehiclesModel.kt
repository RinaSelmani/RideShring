package com.thesis.ridesharing.ui.my_vehicles

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.MyVehiclesActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.Vehicle
import com.thesis.ridesharing.ui.add_vehicle.AddVehicleActivity
import org.greenrobot.eventbus.EventBus

@Suppress("UNCHECKED_CAST")
class MyVehiclesModel(val binding: MyVehiclesActivityBinding, val adapter: MyVehiclesAdapter) {
    private var firestoreDb: FirebaseFirestore
    val VEHICLE_COLLECTION = "Vehicles"
    var uid: String
    val vehicles: MutableList<Vehicle>

    init {
        vehicles = mutableListOf()
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        binding.vehiclesRecycleView.adapter = adapter
        firestoreDb = FirebaseFirestore.getInstance()
    }


    fun getVehicles() {
        vehicles.clear()
        adapter.setListOfVehicles(mutableListOf())
        binding.progressBarHolder.visibility = View.VISIBLE
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
                binding.vehiclesRecycleView.removeAllViews()
                binding.vehiclesRecycleView.adapter = adapter


            }
        }
            .addOnFailureListener {
                Toast.makeText(binding.root.context, "An error has occured", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ERROR", it.localizedMessage.toString())
            }

        binding.progressBarHolder.visibility = View.INVISIBLE


    }

    fun deleteVehicle(position: Int) {
        val vehicle = adapter.vehicles[position]
        val item = HashMap<String, String>()
        item["brand"] = vehicle.brand
        item["model"] = vehicle.model
        item["color"] = vehicle.color
        item["brandAndModel"] = vehicle.getBrandAndModel()
        item["yearOfProduction"] = vehicle.yearOfProduction
        Log.d("ITEM", item.toString())
        firestoreDb.collection(VEHICLE_COLLECTION).document(uid)
            .update("VehicleArray", FieldValue.arrayRemove(item)).addOnSuccessListener {
                Toast.makeText(binding.root.context, "Vehicle deleted", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener() {
                Toast.makeText(
                    binding.root.context,
                    "Vehicle could not be deleted , please try again",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("VEHICLE DELETION", it.localizedMessage)
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