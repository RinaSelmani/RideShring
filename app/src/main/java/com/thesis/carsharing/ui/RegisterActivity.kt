package com.thesis.carsharing.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.RegisterActivityBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: RegisterActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity)
    }


}