package com.thesis.ridesharing.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.SplachScreenActivityBinding
import com.thesis.ridesharing.ui.login.LoginActivity
import com.thesis.ridesharing.ui.register.RegisterActivity

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: SplachScreenActivityBinding
    var SPLASH_SCREEN_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splach_screen_activity)
        Handler().postDelayed({
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }, SPLASH_SCREEN_TIME)


    }
}
