package com.thesis.ridesharing.events

import com.thesis.ridesharing.models.Ride

class RidesPinEvent(val rides: MutableList<Ride>)