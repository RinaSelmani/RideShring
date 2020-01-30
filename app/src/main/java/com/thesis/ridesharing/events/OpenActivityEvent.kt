package com.thesis.ridesharing.events

import android.app.Activity
import com.thesis.ridesharing.models.Ride

class OpenActivityEvent(
    val activity: Activity,
    val clearHistory: Boolean = false,
    val objToPass: String?=""
)