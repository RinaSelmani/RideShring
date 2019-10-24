package com.thesis.carsharing.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.carsharing.MainActivity
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.SplachScreenActivityBinding

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: SplachScreenActivityBinding
    var SPLASH_SCREEN_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splach_screen_activity)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, SPLASH_SCREEN_TIME)


    }
}
