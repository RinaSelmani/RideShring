package com.thesis.ridesharing.models

import java.text.SimpleDateFormat
import java.util.*

class Ride(
    var riderId: String,
    var riderProfile: User,
    var departurePlace: String,
    var departureLatLng: LatLng,
    var arrivalPlace: String,
    val arrivalLatLng: LatLng,
    var dateTime: Date,
    val price: Double,
    val passengers: MutableList<String>,
    val freeSeats: Int,
    val vehicle: Vehicle,
    var id: String,
    val departureAndArrivalAndDate: String,
    val departureAndArrival: String
) {


    constructor() : this(
        "",
        User(),
        "",
        LatLng(),
        "",
        LatLng(),
        Date(),
        0.0,
        mutableListOf<String>(),
        0,
        Vehicle(),
        "", "", ""
    )

    fun showFreeSeats(): String {
        return freeSeats.toString()
    }

    fun getRiderInfo(): String {
        return riderProfile.firstName + " " + riderProfile.secondName
    }

    fun getArrivalAndDeparture(): String {
        return departurePlace + " -> " + arrivalPlace
    }

    fun getVehicleInfo(): String {
        return vehicle.getBrandAndModel()
    }

    fun getRideTimeAndDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy hh:mm")
        return format.format(dateTime)
    }


}