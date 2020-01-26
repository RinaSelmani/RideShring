package com.thesis.ridesharing.ui.register

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.databinding.RegisterActivityBinding
import com.thesis.ridesharing.email_pattern
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.User
import org.greenrobot.eventbus.EventBus


class RegisterModel(val binding: RegisterActivityBinding) {

    val COLLECTION_NAME_KEY = "USERS"
    val REGISTER_FIREBASE_ERROR = "ERROR IN REGISTER"
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    init {
        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
    }

    fun register() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val first_name = binding.firstNameEditLine.text.toString()
        val last_name = binding.lastNameEditLine.text.toString()
        val email = binding.emailEditLine.text.toString()
        val password = binding.passwordNumberEditLine.text.toString()
        val confirm_password = binding.passwordConfNumberEditLine.text.toString()
        val phone_number = binding.phoneNumberEditLine.text.toString()
        val self_description = binding.descEditLine.text.toString()
        if (validate_data(
                email,
                password,
                confirm_password,
                first_name,
                last_name,
                self_description,
                phone_number
            )
        ) {

            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUserId = mAuth!!.currentUser!!.uid.toString().replace("/", "")
                    val firestoreDb = FirebaseFirestore.getInstance()
                    val user =
                        User(
                            first_name,
                            last_name,
                            email,
                            "+383" + phone_number,
                            self_description
                        )
                    firestoreDb.collection(COLLECTION_NAME_KEY).document(currentUserId)
                        .set(user)
                        .addOnSuccessListener {
                            EventBus.getDefault().post(OpenActivityEvent(MainActivity()))
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
                }

            }
                .addOnFailureListener() {
                    Log.d(REGISTER_FIREBASE_ERROR, it.localizedMessage.toString())
                }
        }
    }

    private fun validate_data(
        email: String,
        password: String,
        confirm_password: String,
        first_name: String,
        last_name: String,
        self_description: String,
        phone: String
    ): Boolean {
        if (!email.equals("") and !password.equals("") and !phone.equals("") and
            !confirm_password.equals("") and !first_name.equals("") and
            !last_name.equals("") and !self_description.equals("") and password.equals(
                confirm_password
            )
            and email.matches(email_pattern.toRegex()) and (phone.length == 8)
        ) {
            return true
        }
        binding.progressBarHolder.visibility = View.INVISIBLE
        Toast.makeText(binding.root.context, "Please check you register data", Toast.LENGTH_SHORT)
            .show()

        return false
    }

}