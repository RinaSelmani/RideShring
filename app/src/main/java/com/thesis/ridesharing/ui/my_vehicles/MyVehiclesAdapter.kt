package com.thesis.ridesharing.ui.my_vehicles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.VehicleItemBinding
import com.thesis.ridesharing.events.DeleteVehicle
import com.thesis.ridesharing.models.Vehicle
import org.greenrobot.eventbus.EventBus

class MyVehiclesAdapter : RecyclerView.Adapter<MyVehiclesAdapter.VehicleItem>() {
    var vehicles: MutableList<Vehicle> = mutableListOf()
    override fun onCreateViewHolder(container: ViewGroup, position: Int): VehicleItem {
        val binding: VehicleItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.vehicle_item, container, false
        )
        binding.model = VehicleItem(binding)
        return VehicleItem(binding)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleItem, position: Int) {
        holder.binding.vehicle = vehicles[position]
        holder.binding.position = position
        if (position == vehicles.size - 1) {
            holder.binding.bottomTextview.visibility=View.VISIBLE
        }
    }

    fun setListOfVehicles(vehicles: MutableList<Vehicle>) {
        this.vehicles = vehicles
        notifyDataSetChanged()

    }


    inner class VehicleItem(val binding: VehicleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun deleteVehicle(position: Int) {
            EventBus.getDefault().post(DeleteVehicle(position = position))
            vehicles.removeAt(position)
            notifyDataSetChanged()
        }

    }
}