package com.thesis.ridesharing.events

import android.app.Activity

class OpenActivityEvent(val activity: Activity, val clearHistory: Boolean = false)