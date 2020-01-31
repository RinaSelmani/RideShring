package com.thesis.ridesharing.models

import java.util.*

class User(
    val firstName: String,
    val secondName: String,
    val email: String,
    val phoneNumber: String,
    val description: String,
    val gender: String,
    val age: String
) {
    constructor() : this("", "", "", "", "", "F", "") {

    }

    fun getAgeBasedOnYear(): String {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        return (year.toDouble() - age.toDouble()).toString()
    }

    fun getNameAndLastName():String{
        return "$firstName  $secondName"
    }
}