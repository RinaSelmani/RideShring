package com.thesis.ridesharing.ui.personal_information

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.PersonalInformationActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.User
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PersonalInformationActivity() : Activity() {
    lateinit var binding: PersonalInformationActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.personal_information_activity)
        binding.model=PersonalInformationModel(binding)
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }
}