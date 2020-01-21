package com.thesis.ridesharing.ui.rides.find_ride

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.OneRideItemBinding

class RideItemAdapter : RecyclerView.Adapter<RideItemAdapter.OneRideItemViewModel>() {
    override fun onCreateViewHolder(container: ViewGroup, position: Int): OneRideItemViewModel {
        val binding: OneRideItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.one_ride_item, container, false
        )
        return OneRideItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: OneRideItemViewModel, position: Int) {
    }


    inner class OneRideItemViewModel(val binding: OneRideItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}