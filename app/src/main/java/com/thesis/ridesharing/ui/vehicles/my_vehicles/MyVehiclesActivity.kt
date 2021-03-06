package com.thesis.ridesharing.ui.vehicles.my_vehicles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.MyVehiclesActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.DeleteVehicleEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MyVehiclesActivity : AppCompatActivity() {
    lateinit var binding: MyVehiclesActivityBinding
    lateinit var adapter: MyVehiclesAdapter
    lateinit var model: MyVehiclesModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.my_vehicles_activity)
        val mLayoutManager = LinearLayoutManager(this)
        binding.vehiclesRecycleView.layoutManager = mLayoutManager
        adapter = MyVehiclesAdapter()
        model = MyVehiclesModel(binding, adapter)
        binding.model = model

    }




    override fun onResume() {
        super.onResume()
        binding.model!!.getVehicles()
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

    @Subscribe
    fun event(deleteVehicle: DeleteVehicleEvent) {
        binding.progressBarHolder.visibility = View.VISIBLE
        model.deleteVehicle(position = deleteVehicle.position)
    }
}