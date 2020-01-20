package com.thesis.ridesharing.ui.login

import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.databinding.LoginActivityBinding
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.register.RegisterActivity
import org.greenrobot.eventbus.EventBus

class LoginModel(binding: LoginActivityBinding) {

    fun login() {
        EventBus.getDefault().post(OpenActivityEvent(MainActivity()))
    }

    fun openRegisterActivity() {
        EventBus.getDefault().post(OpenActivityEvent(RegisterActivity()))

    }

}