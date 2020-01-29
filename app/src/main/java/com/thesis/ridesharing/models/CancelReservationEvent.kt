package com.thesis.ridesharing.models

class CancelReservationEvent(
    val rideId: String,
    val numberOfFreeSeats: Int,
    val passangers: MutableList<String>
)