package com.thesis.ridesharing.models

class ReserveRideEvent(
    val rideId: String,
    val numberOfFreeSeats: Int,
    val passangers: MutableList<String>
)