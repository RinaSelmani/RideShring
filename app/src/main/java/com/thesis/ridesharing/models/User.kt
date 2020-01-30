package com.thesis.ridesharing.models

class User(
    val firstName: String,
    val secondName: String,
    val email: String,
    val phoneNumber: String,
    val description: String
){
    constructor() : this("","","","","") {

    }

    fun getNameAndLastName():String{
        return "$firstName  $secondName"
    }
}