package com.thesis.ridesharing.ui.rides

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RideItemBinding
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.rides.ride_detail.RideDetailActivity
import org.greenrobot.eventbus.EventBus

class RideAdapter : RecyclerView.Adapter<RideAdapter.RideItemViewModel>() {
    override fun onCreateViewHolder(container: ViewGroup, position: Int): RideItemViewModel {
        val binding: RideItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.ride_item, container, false
        )
        binding.model = RideItemViewModel(binding)
        return RideItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: RideItemViewModel, position: Int) {
    }


    inner class RideItemViewModel(val binding: RideItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun openRideDetails() {
            EventBus.getDefault().post(OpenActivityEvent(RideDetailActivity()))
        }

    }
}