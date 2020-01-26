package com.thesis.ridesharing.ui.personal_information

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.PersonalInformationActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.User
import org.greenrobot.eventbus.EventBus

class PersonalInformationModel(val binding: PersonalInformationActivityBinding) {
    private lateinit var currentUserDb: DatabaseReference
    val COLLECTION_NAME_KEY = "USERS"

    init {

        getData()
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

    fun closeActivity(){
        EventBus.getDefault().post(CloseActivityEvent())
    }

}