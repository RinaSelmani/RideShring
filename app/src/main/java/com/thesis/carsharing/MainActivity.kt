package com.thesis.carsharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.thesis.carsharing.databinding.MainActivityBinding
import kotlinx.android.synthetic.main.activity_main.*

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
}
