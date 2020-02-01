package com.thesis.ridesharing.ui.rides.past_rides

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.PastRidesActivityBinding
import com.thesis.ridesharing.events.LogOutEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.home.show_rides.RidesFragment
import com.thesis.ridesharing.ui.login.LoginActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class PastRidesActivity : AppCompatActivity() {
    lateinit var binding: PastRidesActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.past_rides_activity)
        open_rides_fragment()
        close_activity()

    }

    private fun open_rides_fragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.rides_container,
            RidesFragment("Archived")
        )
        transaction.commit()
    }

    private fun close_activity() {
        binding.arrowBack.setOnClickListener { onBackPressed() }
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
    fun event(logOutEvent: LogOutEvent) {
        val intent = Intent(
            this, LoginActivity()::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    @Subscribe
    fun event(openActivityEvent: OpenActivityEvent) {
        val intent = Intent(this, openActivityEvent.activity::class.java)
        if (openActivityEvent.objToPass != "") {
            intent.putExtra("RIDE", openActivityEvent.objToPass)
        }
        startActivity(intent)
    }

}