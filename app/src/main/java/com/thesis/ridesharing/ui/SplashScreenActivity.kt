package com.thesis.ridesharing.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.SplachScreenActivityBinding
import com.thesis.ridesharing.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    lateinit var binding: SplachScreenActivityBinding
    lateinit var intentSplach: Intent
    private var SPLASH_SCREEN_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splach_screen_activity)
        Handler().postDelayed({

            if (FirebaseAuth.getInstance().currentUser == null) {
                intentSplach = Intent(this, LoginActivity::class.java)
            } else {
                intentSplach = Intent(this, MainActivity::class.java)
            }
            startActivity(intentSplach)

        }, SPLASH_SCREEN_TIME)


    }
}
