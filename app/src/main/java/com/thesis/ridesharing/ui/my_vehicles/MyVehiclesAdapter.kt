package com.thesis.ridesharing.ui.my_vehicles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.VehicleItemBinding

class MyVehiclesAdapter : RecyclerView.Adapter<MyVehiclesAdapter.VehicleItem>() {
    override fun onCreateViewHolder(container: ViewGroup, position: Int): VehicleItem {
        val binding: VehicleItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.vehicle_item, container, false
        )
        return VehicleItem(binding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: VehicleItem, position: Int) {
    }


    inner class VehicleItem(val binding: VehicleItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}