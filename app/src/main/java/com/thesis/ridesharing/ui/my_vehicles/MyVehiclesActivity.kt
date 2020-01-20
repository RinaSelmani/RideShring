package com.thesis.ridesharing.ui.my_vehicles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.MyVehiclesActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyVehiclesActivity : AppCompatActivity() {
    lateinit var binding: MyVehiclesActivityBinding
    lateinit var adapter: MyVehiclesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.my_vehicles_activity)
        val mLayoutManager = LinearLayoutManager(this)
        binding.vehiclesRecycleView.layoutManager = mLayoutManager
        adapter = MyVehiclesAdapter()
        binding.model = MyVehiclesModel(binding, adapter)

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