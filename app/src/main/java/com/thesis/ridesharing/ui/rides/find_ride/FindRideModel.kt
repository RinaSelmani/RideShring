package com.thesis.ridesharing.ui.rides.find_ride

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.FindRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.GoogleMapsLocationEvent
import com.thesis.ridesharing.models.Ride
import com.thesis.ridesharing.ui.rides.RideAdapter
import org.greenrobot.eventbus.EventBus
import java.util.*

class FindRideModel(val binding: FindRideActivityBinding, val adapter: RideAdapter) {

    private var firestoreDb: FirebaseFirestore
    val RIDE_COLLECTION = "Rides"
    var uid: String
    var initialRidesList = mutableListOf<Ride>()


    init {
        firestoreDb = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        binding.progressBarHolder.visibility = View.VISIBLE
        getAllRides()
    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun openGoogleMapsPlaces(type: String) {
        EventBus.getDefault().post(GoogleMapsLocationEvent(type))
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
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun getAllRides() {
        val time = Date()
        firestoreDb.collection(RIDE_COLLECTION).whereGreaterThanOrEqualTo("dateTime", time).get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                for (d in documents) {
                    val id = d.id
                    val ride: Ride? = d.toObject(Ride::class.java)
                    ride!!.id = id
                    initialRidesList.add(ride)


                }
                adapter.setRidesList(initialRidesList)
                binding.ridesRecycleview.adapter = adapter
                binding.progressBarHolder.visibility = View.GONE

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)
                binding.progressBarHolder.visibility = View.GONE

            }
    }

    fun resetSearch() {
        adapter.setRidesList(initialRidesList)
        binding.ridesRecycleview.adapter = adapter

    }

    fun searchRides() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val departure = binding.departureEditText.text.toString()
        val arrival = binding.arrivalEditText.text.toString()
        val date = binding.dateEditText.text.toString()
        if (validateInputs(departure, arrival)) {
            if (date.equals("")) {
                searchByDepartureAndArrival(departure, arrival)
            } else {
                serachByAllParams(departure, arrival, date)
            }

        }

    }

    fun searchByDepartureAndArrival(departure: String, arrival: String) {

        firestoreDb.collection(RIDE_COLLECTION)
            .whereEqualTo("departureAndArrival", departure + " " + arrival)
            .get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val searchRides = mutableListOf<Ride>()
                for (d in documents) {
                    val id = d.id
                    val ride: Ride? = d.toObject(Ride::class.java)
                    ride!!.id = id
                    searchRides.add(ride)


                }
                adapter.setRidesList(searchRides)
                binding.ridesRecycleview.adapter = adapter
                binding.progressBarHolder.visibility = View.GONE

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)
                binding.progressBarHolder.visibility = View.GONE

            }

    }

    private fun serachByAllParams(departure: String, arrival: String, date: String) {
        val departureAndArrivalAndDate = "$departure $arrival $date"
        firestoreDb.collection(RIDE_COLLECTION)
            .whereEqualTo("departureAndArrivalAndDate", departureAndArrivalAndDate)
            .get()
            .addOnSuccessListener {
                val documents: List<DocumentSnapshot> = it.documents
                val listOfRides = mutableListOf<Ride>()
                for (d in documents) {
                    if (d.exists()) {
                        val id = d.id
                        val ride: Ride? = d.toObject(Ride::class.java)
                        ride!!.id = id
                        listOfRides.add(ride)


                    }

                }
                adapter.setRidesList(listOfRides)
                binding.ridesRecycleview.adapter = adapter

                binding.progressBarHolder.visibility = View.GONE

            }
            .addOnFailureListener {
                Log.d("ERORr", it.localizedMessage)

            }
    }


    fun validateInputs(departure: String, arrival: String): Boolean {
        if (!departure.equals("") and !arrival.equals("")) {
            return true
        }
        Toast.makeText(
            binding.root.context,
            "Fill both departure and arrival please",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }


    fun swapValues() {
        val departure = binding.departureEditText.text.toString()
        binding.departureEditText.setText(binding.arrivalEditText.text.toString())
        binding.arrivalEditText.setText(departure)

    }


}