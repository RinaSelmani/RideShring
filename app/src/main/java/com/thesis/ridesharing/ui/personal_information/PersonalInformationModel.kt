package com.thesis.ridesharing.ui.personal_information

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.R
import com.thesis.ridesharing.currentUser
import com.thesis.ridesharing.databinding.PersonalInformationActivityBinding
import com.thesis.ridesharing.email_pattern
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.years
import org.greenrobot.eventbus.EventBus
import java.util.*

class PersonalInformationModel(val binding: PersonalInformationActivityBinding) {
    val USERS = "USERS"
    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    var uid: String
    var rideIdS = mutableListOf<String>()

    init {
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        firestoreDb = FirebaseFirestore.getInstance()
        binding.user = currentUser
        if (currentUser.gender.equals("M")) {
            binding.profileImageView.setBackgroundResource(R.drawable.ic_man)
        }
        rideIdS = getRideIds()
    }


    private fun validate_data(
        email: String,
        first_name: String,
        last_name: String,
        self_description: String,
        phone: String, gender: String, age: String
    ): Boolean {
        if (!email.equals("") and !phone.equals("") and !first_name.equals("") and
            !last_name.equals("") and !self_description.equals("")
            and email.matches(email_pattern.toRegex()) and (phone.length == 8) and (!gender.equals(""))
            and (!age.equals(""))
        ) {
            return true
        }
        binding.progressBarHolder.visibility = View.INVISIBLE
        Toast.makeText(binding.root.context, "Please check you register data", Toast.LENGTH_SHORT)
            .show()

        return false
    }

    fun update() {
        binding.progressBarHolder.visibility = View.VISIBLE
        binding.updateButton.requestFocus()
        loseFosucOfEditLines()
        val first_name = binding.firstNameEditLine.text.toString()
        val last_name = binding.lastNameEditLine.text.toString()
        val email = binding.emailEditLine.text.toString()
        val phone_number = binding.phoneNumberTextText.text.toString()
        val self_description = binding.descEditLine.text.toString()
        val gender = binding.genderEditText.text.toString()
        val age = binding.ageEditText.text.toString()
        if (validate_data(
                email,
                first_name,
                last_name,
                self_description,
                phone_number,
                gender,
                age
            )
        ) {

            val user = User(
                firstName = first_name,
                secondName = last_name,
                email = email,
                phoneNumber = phone_number,
                description = self_description,
                gender = gender,
                age = age
            )
            firestoreDb.collection(USERS).document(uid).set(user).addOnCompleteListener {
                if(rideIdS.size>0){
                    updateProfiles(rideIdS, user)
                }else{
                    Toast.makeText(
                        binding.root.context,
                        "Profile Updated Succesfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarHolder.visibility=View.GONE
                }


            }

                .addOnFailureListener {
                    Toast.makeText(
                        binding.root.context,
                        "Error while updating the profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarHolder.visibility=View.GONE

                }
        }


    }

    fun getRideIds(): MutableList<String> {
        val rideIds = mutableListOf<String>()
        firestoreDb.collection("RIDES_USERS").document(uid).collection(uid).get()
            .addOnSuccessListener {
                for (i in it.documents) {
                    rideIds.add(i[uid].toString())
                }
            }

        return rideIds
    }

    fun updateProfiles(rideIds: MutableList<String>, user: User) {
        val hashMap = HashMap<String, User>()
        hashMap["riderProfile"] = user
        for (i in rideIds) {
            firestoreDb.collection(RIDE_COLLECTION).document(i).update(hashMap as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(
                        binding.root.context,
                        "Profile Updated Succesfully",
                        Toast.LENGTH_SHORT
                    ).show()


                }
            binding.progressBarHolder.visibility=View.GONE

        }


    }


    fun closeActivity(){
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun loseFosucOfEditLines() {
        binding.firstNameEditLine.clearFocus()
        binding.lastNameEditLine.clearFocus()
        binding.emailEditLine.clearFocus()
        binding.phoneNumberTextText.clearFocus()
        binding.descEditLine.clearFocus()

    }

    fun selectSex() {
        val builder = AlertDialog.Builder(binding.root.context, R.style.AlertDialogCustom)
        builder.setTitle("Choose Gender")
        val items = listOf<String>("F", "M").toTypedArray()

        var checkedItem = 0
        builder.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            checkedItem = which
        }


        builder.setPositiveButton("OK") { dialog, which ->

            binding.genderEditText.setText(items[checkedItem])


        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun selectAge() {
        val builder = AlertDialog.Builder(binding.root.context, R.style.AlertDialogCustom)
        builder.setTitle("Choose Age")
        val items = years().toTypedArray()

        var checkedItem = 0
        builder.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            checkedItem = which
        }


        builder.setPositiveButton("OK") { dialog, which ->

            binding.ageEditText.setText(items[checkedItem])


        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }


}

