package com.thesis.ridesharing.events

class ReserveRideEvent(
    val rideId: String,
    val numberOfFreeSeats: Int,
    val passangers: MutableList<String>
)