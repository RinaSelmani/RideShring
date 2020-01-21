package com.thesis.ridesharing.ui.rides.find_ride

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.FindRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.rides.RideAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FindRideActivity : AppCompatActivity() {
    lateinit var binding: FindRideActivityBinding
    lateinit var adapter: RideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.find_ride_activity)
        val mLayoutManager = LinearLayoutManager(this)
        binding.ridesRecycleview.layoutManager = mLayoutManager
        adapter = RideAdapter()
        binding.model = FindRideModel(binding, adapter)
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
    fun event(openActivityEvent: OpenActivityEvent) {
        val intent = Intent(this, openActivityEvent.activity::class.java)
        startActivity(intent)
    }

    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }
}