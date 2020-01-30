package com.thesis.ridesharing.ui.personal_information

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.currentUser
import com.thesis.ridesharing.databinding.PersonalInformationActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.User
import org.greenrobot.eventbus.EventBus

class PersonalInformationModel(val binding: PersonalInformationActivityBinding) {
    val COLLECTION_NAME_KEY = "USERS"

    init {
        binding.user = currentUser
    }
    fun getData() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val uid = FirebaseAuth.getInstance().uid
        val firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection(COLLECTION_NAME_KEY).document(uid!!).get().addOnSuccessListener {
            if (it != null) {
                binding.progressBarHolder.visibility = View.GONE
                val user = it.toObject(User::class.java)
                binding.user = user

            }

        }
            .addOnFailureListener() {
                binding.progressBarHolder.visibility = View.GONE

                Log.d("DOCUMENT SNAP", it.localizedMessage)
            }
    }

    fun update() {

    }

    fun validateData() {

    }

    fun closeActivity(){
        EventBus.getDefault().post(CloseActivityEvent())
    }

}