package com.thesis.ridesharing.models

import java.io.Serializable
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
    val departureAndArrival: String,
    val miliseconds: Long,
    val rideDescription:String
)  {


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
        "", "", "", 0,""
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
        return getDate(miliseconds, "dd/MM/yyyy HH:mm ")
    }

    fun getSeatsInfo(): String {
        val numberOfSeats = vehicle.numberOfSeats - 1
        return "$freeSeats/$numberOfSeats"
    }


    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun getPriceToString():String{
        return price.toString()
    }


}