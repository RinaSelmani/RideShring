package com.thesis.ridesharing.ui.register

import com.thesis.ridesharing.databinding.RegisterActivityBinding
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.login.LoginActivity
import org.greenrobot.eventbus.EventBus

class RegisterModel(val binding: RegisterActivityBinding) {

    fun register() {
        EventBus.getDefault().post(OpenActivityEvent(LoginActivity()))
    }
}