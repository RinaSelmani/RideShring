package com.thesis.ridesharing.ui.personal_information

import com.thesis.ridesharing.databinding.PersonalInformationActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import org.greenrobot.eventbus.EventBus

class PersonalInformationModel(val binding: PersonalInformationActivityBinding) {

    fun update() {

    }


    fun closeActivity(){
        EventBus.getDefault().post(CloseActivityEvent())
    }

}