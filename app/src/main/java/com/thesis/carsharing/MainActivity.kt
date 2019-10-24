package com.thesis.carsharing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thesis.carsharing.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
