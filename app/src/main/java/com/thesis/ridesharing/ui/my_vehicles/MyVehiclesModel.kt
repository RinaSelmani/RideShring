package com.thesis.ridesharing.ui.my_vehicles

import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.add_car.AddCarActivity
import org.greenrobot.eventbus.EventBus

class MyVehiclesModel {


    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun openAddCarActivity() {
        EventBus.getDefault().post(OpenActivityEvent(AddCarActivity()))
    }
}