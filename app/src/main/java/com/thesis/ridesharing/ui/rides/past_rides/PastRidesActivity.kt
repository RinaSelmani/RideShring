package com.thesis.ridesharing.ui.rides.past_rides

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.PastRidesActivityBinding
import com.thesis.ridesharing.ui.home.RidesFragment


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
        transaction.replace(R.id.rides_container, RidesFragment())
        transaction.commit()
    }

    private fun close_activity() {
        binding.arrowBack.setOnClickListener { onBackPressed() }
    }

}