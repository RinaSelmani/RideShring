package com.thesis.ridesharing.events

class CancelReservationEvent(
    val rideId: String,
    val numberOfFreeSeats: Int,
    val passangers: MutableList<String>
)