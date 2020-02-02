package com.thesis.ridesharing.ui.rides.add_ride

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.AddRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.GoogleMapsLocationEvent
import com.thesis.ridesharing.models.LatLng
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.models.Vehicle
import com.thesis.ridesharing.ui.my_vehicles.MyVehiclesAdapter
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class AddRideViewModel(val binding: AddRideActivityBinding, val adapter: MyVehiclesAdapter) {

    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    val VEHICLE_COLLECTION = "Vehicles"
    val RIDE_USERS_COLLECTION = "Ride_Users"
    val USERS_COLLECTION = "USERS"
    lateinit var currentUser: User
    var uid: String
    var numberOfSeats = 0
    var departureLatLng = LatLng()
    var arrivalLatLng = LatLng()
    var user = User()

    init {
        binding.progressBarHolder.visibility = View.VISIBLE
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        binding.myVehiclesRecycleView.adapter = adapter
        firestoreDb = FirebaseFirestore.getInstance()
        getVehicles()
        getUser()
    }


    private fun getVehicles() {
        val myVehicles: MutableList<Vehicle> = mutableListOf()
        adapter.setListOfVehicles(mutableListOf())
        binding.progressBarHolder.visibility = View.VISIBLE
        firestoreDb.collection(VEHICLE_COLLECTION).document(uid).collection(uid).get()
            .addOnSuccessListener {

                for (i in it.documents) {
                    val vehicle = i.toObject(Vehicle::class.java)
                    vehicle?.vehicleId = i.id
                    myVehicles.add(vehicle!!)
                }

                adapter.setListOfVehicles(myVehicles)
                binding.myVehiclesRecycleView.adapter = adapter
                if (adapter.vehicles.size > 0) {
                    val number_of_seats = adapter.vehicles[adapter.checkedPosition].numberOfSeats
                    if (number_of_seats != 0) {
                        numberOfSeats = adapter.vehicles[adapter.checkedPosition].numberOfSeats - 1
                        binding.freeSeatsDescpEditText.setText(numberOfSeats.toString())
                    }
                } else {
                    binding.priceAndSeatsContainer.visibility = View.GONE
                    binding.missingCarWarningTextView.visibility = View.VISIBLE
                }


                binding.progressBarHolder.visibility = View.GONE




            }

            .addOnFailureListener {
                Toast.makeText(binding.root.context, "An error has occured", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ERROR", it.localizedMessage.toString())
                binding.progressBarHolder.visibility = View.GONE

            }

        binding.progressBarHolder.visibility = View.GONE


    }


    fun getUser() {
        val firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection(USERS_COLLECTION).document(uid).get().addOnSuccessListener {
            if (it != null) {
                binding.progressBarHolder.visibility = View.GONE
                user = it.toObject(User::class.java)!!

            }

        }

    }
    fun openGoogleMapsPlaces(type: String) {
        EventBus.getDefault().post(GoogleMapsLocationEvent(type))
    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun datePicker() {

        // Get Current Date
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            binding.root.context,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val date_time = "" + (monthOfYear + 1) + "/" + dayOfMonth.toString() + "/" + year
                binding.dateEditText.setText(date_time)
                //*************Call Time Picker Here ********************
                timePicker()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.minDate = (System.currentTimeMillis())
        datePickerDialog.show()
    }

    fun timePicker() {
        // Get Current Time
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            binding.root.context,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                var hour = hourOfDay.toString()
                if (hour.length == 1) {
                    hour = "0" + hour.toString()
                }

                binding.timeEditText.setText(hour + ":" + minute.toString())
            }, mHour, mMinute, true
        )

        timePickerDialog.show()
    }

    fun setFreeSeats() {
        showDialog(numberOfSeats - 1)

    }

    fun showDialog(number_of_seats: Int) {

        val builder = AlertDialog.Builder(
            binding.root.context,
            com.thesis.ridesharing.R.style.AlertDialogCustom
        )
        builder.setTitle("Choose Seats")
        val items = mutableListOf<String>()
        for (i in 1..number_of_seats) {
            items.add(i.toString())
        }
        var checkedItem = 0
        builder.setSingleChoiceItems(items.toTypedArray(), checkedItem) { dialog, which ->
            checkedItem = which
        }

        builder.setPositiveButton("OK") { dialog, which ->
            binding.freeSeatsDescpEditText.setText(items[checkedItem])
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun addRide() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val departure = binding.departureEditText.text.toString()
        val arrival = binding.arrivalEditText.text.toString()
        val vehicle = adapter.vehicles[adapter.checkedPosition]
        val price = binding.priceDescpEditText.text.toString()
        val freeSeats = binding.freeSeatsDescpEditText.text.toString()
        val date = binding.dateEditText.text.toString()
        val time = binding.timeEditText.text.toString()
        val description = binding.rideDecpEditText.text.toString()
        if (validateData(departure, arrival, price, freeSeats, date, time, description)) {

            val pattern = "MM/dd/yyyy HH:mm"
            val dateFormated = SimpleDateFormat(pattern).parse(date+" "+time)
            val miliseconds = dateFormated.time
            val ride = Ride(
                riderId = uid,
                riderProfile = user,
                departurePlace = departure,
                departureLatLng = departureLatLng,
                arrivalPlace = arrival,
                arrivalLatLng = arrivalLatLng,
                dateTime = dateFormated!!,
                price = price.toDouble(),
                passengers = mutableListOf(),
                freeSeats = freeSeats.toInt(),
                vehicle = vehicle,
                id = "",
                departureAndArrivalAndDate = departure + " " + arrival + " " + date,
                departureAndArrival = departure + " " + arrival,
                miliseconds = miliseconds,
                rideDescription = description
            )
            var rideId = ""
            firestoreDb.collection(RIDE_COLLECTION).add(ride)
                .addOnSuccessListener {

                    rideId = it.id
                    binding.progressBarHolder.visibility = View.GONE



                }
                .addOnCompleteListener {
                    addRideUserInfo(rideId)
                }
                .addOnFailureListener() {
                    binding.progressBarHolder.visibility = View.GONE

                    Log.d("ERROR RIDE", it.localizedMessage)

                }


        }
    }


    fun validateData(
        departure: String,
        arrival: String,
        price: String,
        freeSeats: String,
        date: String,
        time: String,
        description: String
    ): Boolean {
        if (!departure.equals("") and !arrival.equals("") and !price.equals("") and
            !freeSeats.equals("") and !date.equals("") and !time.equals("") and !description.equals(
                ""
            )
        ) {

            return true
        }
        binding.progressBarHolder.visibility = View.GONE

        Toast.makeText(binding.root.context, "Fill all the data please", Toast.LENGTH_SHORT).show()
        return false

    }

    fun swapDepartureAndArrival() {
        val arrival = binding.arrivalEditText.text.toString()
        val departure = binding.departureEditText.text.toString()
        if (!departure.equals("") and !arrival.equals("")) {
            binding.departureEditText.setText(arrival)
            binding.arrivalEditText.setText(departure)
            val swapHelper = arrivalLatLng
            arrivalLatLng = departureLatLng
            departureLatLng = swapHelper
        }
    }

    fun addRideUserInfo(rideId: String) {
        val hashMap = HashMap<String, String>()
        hashMap[uid] = rideId
        firestoreDb.collection("RIDES_USERS").document(uid).collection(uid).add(hashMap)
            .addOnSuccessListener {
                Toast.makeText(
                    binding.root.context,
                    "Ride added successfully",
                    Toast.LENGTH_SHORT
                ).show()
                EventBus.getDefault().post(CloseActivityEvent())

            }
        binding.progressBarHolder.visibility = View.GONE
    }

}