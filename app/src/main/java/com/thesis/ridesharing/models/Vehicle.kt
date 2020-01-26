package com.thesis.ridesharing.models

class Vehicle(
    val brand: String,
    val model: String,
    val color: String,
    val yearOfProduction: String
) {

    constructor() : this("", "", "", "")

    fun getBrandAndModel(): String {
        return brand + " " + model
    }

    override fun toString(): String {
        return "Vehicle(brand='$brand', model='$model', color='$color', yearOfProduction='$yearOfProduction')"
    }


}