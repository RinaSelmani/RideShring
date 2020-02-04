package com.thesis.ridesharing.ui.register

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RegisterActivityBinding
import com.thesis.ridesharing.email_pattern
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.ui.login.LoginActivity
import com.thesis.ridesharing.years
import org.greenrobot.eventbus.EventBus


class RegisterModel(val binding: RegisterActivityBinding) {

    val COLLECTION_NAME_KEY = "USERS"
    val REGISTER_FIREBASE_ERROR = "ERROR IN REGISTER"
    val EMAIL_PHONE_COLLECTION = "EMAIL_PHONE"
    private var firestoreDb: FirebaseFirestore
    var phones = mutableListOf<String>()


    private var mAuth: FirebaseAuth? = null

    init {
        firestoreDb = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        getPhones()
    }

    fun register() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val first_name = binding.firstNameEditText.text.toString()
        val last_name = binding.lastNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordNumberEditText.text.toString()
        val confirm_password = binding.passwordConfNumberEditText.text.toString()
        val phone_number = binding.phoneNumberTextText.text.toString()
        val self_description = binding.descEditText.text.toString()
        val gender = binding.genderEditText.text.toString()
        val age = binding.ageEditText.text.toString()
        if (validate_data(
                email,
                password,
                confirm_password,
                first_name,
                last_name,
                self_description,
                phone_number,
                gender,
                age
            ) and checkPhone(phone_number)
        ) {

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUserId = mAuth!!.currentUser!!.uid.toString().replace("/", "")
                    val firestoreDb = FirebaseFirestore.getInstance()
                    verifyEmail()
                    val user =
                        User(
                            first_name,
                            last_name,
                            email,
                            phone_number,
                            self_description,
                            gender, age
                        )
                    firestoreDb.collection(COLLECTION_NAME_KEY).document(currentUserId)
                        .set(user)
                        .addOnSuccessListener {
                            addPhone(phone_number)
                        }
                        .addOnFailureListener {
                            Log.d(REGISTER_FIREBASE_ERROR, it.localizedMessage)
                        }

                } else {
                    Toast.makeText(
                        binding.root.context,
                        "This user already exists",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBarHolder.visibility = View.GONE

                }

            }
                .addOnFailureListener() {
                    binding.progressBarHolder.visibility = View.GONE
                    Log.d(REGISTER_FIREBASE_ERROR, it.localizedMessage.toString())
                }
        } else {
            binding.progressBarHolder.visibility = View.GONE
        }
    }

    private fun validate_data(
        email: String,
        password: String,
        confirm_password: String,
        first_name: String,
        last_name: String,
        self_description: String,
        phone: String, gender: String, age: String
    ): Boolean {
        if (!email.equals("") and !password.equals("") and !phone.equals("") and
            !confirm_password.equals("") and !first_name.equals("") and
            !last_name.equals("") and !self_description.equals("") and password.equals(
                confirm_password
            )
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

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
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

    fun getPhones() {
        firestoreDb.collection(EMAIL_PHONE_COLLECTION).get().addOnSuccessListener {
            for (documents in it.documents) {
                phones.add(documents["phone"].toString())

            }


        }
    }

    fun checkPhone(phone: String): Boolean {
        if (phone in phones) {
            Toast.makeText(
                binding.root.context,
                "There is another user already using this number",
                Toast.LENGTH_SHORT
            ).show()

            return false

        }
        return true
    }

    fun verifyEmail() {
        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification().addOnSuccessListener {
            Toast.makeText(
                binding.root.context,
                "An email to verify your account was sent to your email address",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    fun addPhone(phone: String) {
        val hashMapEmailPhone = HashMap<String, String>()
        hashMapEmailPhone["phone"] = phone
        val uid=FirebaseAuth.getInstance().currentUser!!.uid

        firestoreDb.collection(EMAIL_PHONE_COLLECTION).document(uid).set(hashMapEmailPhone)
            .addOnSuccessListener {
                EventBus.getDefault().post(OpenActivityEvent(LoginActivity()))

            }
            .addOnFailureListener {
                Log.d("ERROR", it.localizedMessage.toString())

            }

    }

}