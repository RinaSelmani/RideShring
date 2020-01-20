package com.thesis.ridesharing.ui.home.profile

import com.thesis.ridesharing.databinding.ProfileFragmentBinding
import com.thesis.ridesharing.events.LogOutEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.personal_information.PersonalInformationActivity
import com.thesis.ridesharing.ui.rides.past_rides.PastRidesActivity
import org.greenrobot.eventbus.EventBus

class ProfileViewModel(val binding: ProfileFragmentBinding) {

    fun openPersonalInformationActivity() {
        EventBus.getDefault().post(OpenActivityEvent(PersonalInformationActivity()))
    }

    fun logout() {
        EventBus.getDefault().post(LogOutEvent())
    }

    fun openPastRidesActivity() {
        EventBus.getDefault().post(OpenActivityEvent(PastRidesActivity()))
    }
}