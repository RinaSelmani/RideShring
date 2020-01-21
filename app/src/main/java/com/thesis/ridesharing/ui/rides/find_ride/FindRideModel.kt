package com.thesis.ridesharing.ui.rides.find_ride

import com.thesis.ridesharing.databinding.FindRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.ui.rides.RideAdapter
import org.greenrobot.eventbus.EventBus

class FindRideModel(val binding: FindRideActivityBinding, val adapter: RideAdapter) {
    init {
        binding.ridesRecycleview.adapter = adapter
    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }
}