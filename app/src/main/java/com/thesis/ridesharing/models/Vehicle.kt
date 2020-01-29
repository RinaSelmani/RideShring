package com.thesis.ridesharing.models

class Vehicle(
    var vehicleId: String,
    val brand: String,
    val model: String,
    val color: String,
    val yearOfProduction: String,
    val numberOfSeats: Int
) {

    constructor() : this(
        "", "", "", "", "", 0
    )

    fun getBrandAndModel(): String {
        return brand + " " + model
    }

    override fun toString(): String {
        return "Vehicle(brand='$brand', model='$model', color='$color', yearOfProduction='$yearOfProduction')"
    }

    fun getSeats(): String {
        return numberOfSeats.toString()
    }


}