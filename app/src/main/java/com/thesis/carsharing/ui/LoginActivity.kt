package com.thesis.carsharing.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity)
    }

}