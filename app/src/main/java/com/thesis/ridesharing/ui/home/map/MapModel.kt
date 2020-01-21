package com.thesis.ridesharing.ui.home.map

import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.rides.find_ride.FindRideActivity
import org.greenrobot.eventbus.EventBus

class MapModel {


    fun findRide() {
        EventBus.getDefault().post(OpenActivityEvent(FindRideActivity()))
    }
}