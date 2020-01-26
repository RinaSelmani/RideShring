package com.thesis.ridesharing.ui.rides.add_ride

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.AddRideActivityBinding
import com.thesis.ridesharing.ui.my_vehicles.MyVehiclesAdapter

class AddRideActivity : AppCompatActivity() {

    lateinit var binding: AddRideActivityBinding
    lateinit var adapter: MyVehiclesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_ride_activity)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.myVehiclesRecycleView.layoutManager = mLayoutManager
        adapter = MyVehiclesAdapter(isAddRide = true)
        binding.model = AddRideViewModel(binding, adapter)
    }
}