package com.thesis.ridesharing.ui.vehicles.add_vehicle

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.AddVehicleActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenDialogEvent
import com.thesis.ridesharing.models.Vehicle
import org.greenrobot.eventbus.EventBus

class AddVehicleModel(val binding: AddVehicleActivityBinding) {
    private var firestoreDb: FirebaseFirestore
    val VEHICLE_COLLECTION = "Vehicles"
    val SAVE_VEHICLE_ERROR = "Save Vehicle Error"

    init {
        firestoreDb = FirebaseFirestore.getInstance()
    }


    fun addVehicle() {
        if (validateData()) {
            binding.progressBarHolder.visibility = View.VISIBLE
            val brandText = binding.brandEditText.text.toString()
            val modelText = binding.modelEditText.text.toString()
            val colorText = binding.colorEditText.text.toString()
            val yearOfProductionText = binding.yearOfProductionEditText.text.toString()
            val numberOfSeats = binding.seatsEditText.text.toString().toInt()
            val vehicle = Vehicle(
                vehicleId = "",
                brand = brandText,
                model = modelText,
                color = colorText,
                yearOfProduction = yearOfProductionText,
                numberOfSeats = numberOfSeats
            )
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val documentReference =
                firestoreDb.collection(VEHICLE_COLLECTION).document(uid).collection(uid)
                    .add(vehicle).addOnSuccessListener {
                    Toast.makeText(binding.root.context, "Vehicle Saved", Toast.LENGTH_SHORT).show()
                    EventBus.getDefault().post(CloseActivityEvent())

                }
                    .addOnFailureListener {
                        Log.d(SAVE_VEHICLE_ERROR, it.localizedMessage)

                    }

            }
            binding.progressBarHolder.visibility = View.INVISIBLE

        }



    fun validateData(): Boolean {
        val brandHint = binding.brandEditText.text.toString()
        val modelHint = binding.modelEditText.text.toString()
        val colorHint = binding.colorEditText.text.toString()
        val yearOfProductionHint = binding.yearOfProductionEditText.text.toString()
        val seats = binding.seatsEditText.text.toString()
        if (!brandHint.equals("") and !modelHint.equals("") and !colorHint.equals("") and !yearOfProductionHint.equals(
                ""
            ) and !seats.equals("")
        ) {
            return true
        }
        Toast.makeText(
            binding.root.context,
            "Please fill all the data for vehicle",
            Toast.LENGTH_SHORT
        ).show()
        return false


    }


    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())

    }

    fun addSeat() {
        EventBus.getDefault().post(OpenDialogEvent("Seats"))
    }
    fun addBrand() {
        EventBus.getDefault().post(OpenDialogEvent("Brand"))
    }

    fun addModel() {
        EventBus.getDefault().post(OpenDialogEvent("Model"))

    }

    fun addColor() {
        EventBus.getDefault().post(OpenDialogEvent("Color"))

    }


    fun addYear() {
        EventBus.getDefault().post(OpenDialogEvent("Year"))

    }

}