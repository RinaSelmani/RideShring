package com.thesis.ridesharing

import com.thesis.ridesharing.models.User
import java.text.SimpleDateFormat

val email_pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
val car_brands = hashMapOf(
    "Audi" to arrayOf("Audi A1", "Audi A3", "Audi A5"),
    "BMW" to arrayOf("X1", "X2", "X3", "X5"),
    "Mercedes" to arrayOf("A-Class", "AMG GT", "C-Class", "CLA"),
    "Opel" to arrayOf("Astra Sports  to urer", "Corsa"),
    "Porsche" to arrayOf("Panamera"),
    "Wolkswagen" to arrayOf("Arteon", "Atlas", "Beetle")
)
lateinit var currentUser: User
val colors = arrayOf("Blue", "Red", "Black", "Yellow", "Gray", "White")
val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
fun years(): ArrayList<String> {
    val years = arrayListOf<String>()
    for (a in 1995..2020) {
        years.add(a.toString())
    }

    return years
}

fun seats(): ArrayList<String> {
    val seats = arrayListOf<String>()
    for (a in 2..10) {
        seats.add(a.toString())
    }

    return seats
}
