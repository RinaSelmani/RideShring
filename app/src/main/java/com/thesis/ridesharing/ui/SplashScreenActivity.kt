package com.thesis.ridesharing.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.SplachScreenActivityBinding
import com.thesis.ridesharing.ui.login.LoginActivity


private val animationStarted = false

class SplashScreenActivity : Activity() {

    lateinit var binding: SplachScreenActivityBinding
    lateinit var intentSplach: Intent
    private var SPLASH_SCREEN_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splach_screen_activity)
        Glide.with(this)
            .load(R.mipmap.splach)
            .into(binding.splachImageView)
        Glide.with(this)
            .load(R.mipmap.cover)
            .into(binding.rideSharingLogo)

        if (checkPermissions()) {
            enterInApp()

        } else {
            requestPermissions()
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (!hasFocus || animationStarted) {
            return
        }
        animate()
        super.onWindowFocusChanged(hasFocus)
    }

    fun animate() {
        binding.rideSharingLogo.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        binding.rideSharingLogo.startAnimation(animation)

    }

    fun enterInApp() {
        Handler().postDelayed({

            if (FirebaseAuth.getInstance().currentUser == null) {
                intentSplach = Intent(this, LoginActivity::class.java)
            } else {
                intentSplach = Intent(this, MainActivity::class.java)
            }
            startActivity(intentSplach)

        }, SPLASH_SCREEN_TIME)

    }

    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        startLocationPermissionRequest()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                requestPermissions()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                enterInApp()

            } else {
                enterInApp()
            }
        }
    }


}
