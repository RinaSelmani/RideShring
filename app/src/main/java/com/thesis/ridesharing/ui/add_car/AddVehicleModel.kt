package com.thesis.ridesharing.ui.add_car

import com.thesis.ridesharing.databinding.AddVehicleActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenDialogEvent
import org.greenrobot.eventbus.EventBus

class AddVehicleModel(val binding: AddVehicleActivityBinding) {


    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())

    }

    fun addBrand() {
        EventBus.getDefault().post(OpenDialogEvent("Brand"))
    }

    fun addModel() {
        EventBus.getDefault().post(OpenDialogEvent("Model"))

    }

    fun addColor() {
        EventBus.getDefault().post(OpenDialogEvent("Color"))

    }

    fun addVehicle() {

    }

}