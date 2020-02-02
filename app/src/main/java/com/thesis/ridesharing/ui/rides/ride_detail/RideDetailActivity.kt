package com.thesis.ridesharing.ui.rides.ride_detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RideDetailsActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.models.ChangeReservationEvent
import com.thesis.ridesharing.ui.rides.find_ride.RideItemAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class RideDetailActivity() : AppCompatActivity() {
    lateinit var binding: RideDetailsActivityBinding
    lateinit var adapter: RideItemAdapter
    val UPDATE_RIDE_ITEM = "Reservation"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.ride_details_activity)
        val ride = intent.getStringExtra("RIDE")
        binding.model = RideDetailModel(binding, ride)

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

    @Subscribe
    fun event(changeReservationEvent: ChangeReservationEvent) {
        val resultIntent = Intent()
        resultIntent.putExtra(UPDATE_RIDE_ITEM, changeReservationEvent.numberofSeatsAndReserveText)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}