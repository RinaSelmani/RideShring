package com.thesis.ridesharing.ui.rides.ride_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RideDetailsActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.ui.rides.find_ride.RideItemAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class RideDetailActivity : AppCompatActivity() {
    lateinit var binding: RideDetailsActivityBinding
    lateinit var adapter: RideItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.ride_details_activity)
        binding.model = RideDetailModel(binding)
        adapter = RideItemAdapter()
        val mLayoutManager = LinearLayoutManager(this)
        binding.ridesStopsRecycleview.layoutManager = mLayoutManager
        binding.ridesStopsRecycleview.adapter = adapter
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }
}