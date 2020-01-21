package com.thesis.ridesharing.ui.rides.ride_detail

import com.thesis.ridesharing.databinding.RideDetailsActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import org.greenrobot.eventbus.EventBus

class RideDetailModel(val binding: RideDetailsActivityBinding) {

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }
}