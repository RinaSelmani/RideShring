package com.thesis.ridesharing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.thesis.ridesharing.databinding.MainActivityBinding
import com.thesis.ridesharing.events.LogOutEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation_controller = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigateBottom(navigation_controller)



    }

    private fun navigateBottom(nav_controller: NavController) {
        bottom_navigation.let {
            NavigationUI.setupWithNavController(it, nav_controller)
        }
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
    fun event(logOutEvent: LogOutEvent) {
        val intent = Intent(
            this, LoginActivity()::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }


}
