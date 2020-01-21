package com.thesis.ridesharing.ui.my_vehicles

import com.thesis.ridesharing.databinding.MyVehiclesActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.add_vehicle.AddVehicleActivity
import org.greenrobot.eventbus.EventBus

class MyVehiclesModel(binding: MyVehiclesActivityBinding, adapter: MyVehiclesAdapter) {
    init {
        binding.vehiclesRecycleView.adapter = adapter
    }


    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun openAddCarActivity() {
        EventBus.getDefault().post(OpenActivityEvent(AddVehicleActivity()))
    }
}