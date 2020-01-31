package com.thesis.ridesharing.ui.home.profile

import com.google.firebase.auth.FirebaseAuth
import com.thesis.ridesharing.R
import com.thesis.ridesharing.currentUser
import com.thesis.ridesharing.databinding.ProfileFragmentBinding
import com.thesis.ridesharing.events.LogOutEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.ui.my_vehicles.MyVehiclesActivity
import com.thesis.ridesharing.ui.personal_information.PersonalInformationActivity
import com.thesis.ridesharing.ui.rides.past_rides.PastRidesActivity
import org.greenrobot.eventbus.EventBus

class ProfileViewModel(val binding: ProfileFragmentBinding) {
    val COLLECTION_NAME_KEY = "USERS"

    init {
        binding.nameTextView.setText(currentUser.getNameAndLastName())
        if (currentUser.gender.equals("M")) {
            binding.profileImageView.setBackgroundResource(R.drawable.ic_man)
        }
    }


    fun openPersonalInformationActivity() {
        EventBus.getDefault().post(OpenActivityEvent(PersonalInformationActivity()))
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        EventBus.getDefault().post(LogOutEvent())
    }

    fun openPastRidesActivity() {
        EventBus.getDefault().post(OpenActivityEvent(PastRidesActivity()))
    }

    fun openMyVehiclesActivity() {
        EventBus.getDefault().post(OpenActivityEvent(MyVehiclesActivity()))
    }


}