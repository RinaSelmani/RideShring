package com.thesis.ridesharing.ui.rides.find_ride

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.FindRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.GoogleMapsLocationEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.CancelReservationEvent
import com.thesis.ridesharing.models.ReserveRideEvent
import com.thesis.ridesharing.ui.rides.RideAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


class FindRideActivity : Activity() {
    lateinit var binding: FindRideActivityBinding
    lateinit var adapter: RideAdapter
    var AUTOCOMPLETE_REQUEST_CODE = 1
    var typeToFillLocation: String = ""
    val UPDATE_RIDE_ITEM = 2002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.find_ride_activity)
        val mLayoutManager = LinearLayoutManager(this)
        binding.ridesRecycleview.layoutManager = mLayoutManager
        adapter = RideAdapter()


        val apiKey = getString(R.string.google_key_api)

        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey)
        }
        val placesClient = Places.createClient(this)
        binding.model = FindRideModel(binding, adapter)
    }


    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun event(openActivityEvent: OpenActivityEvent) {
        val intent = Intent(this, openActivityEvent.activity::class.java)
        if (openActivityEvent.objToPass != "") {
            intent.putExtra("RIDE", openActivityEvent.objToPass)
        }
        startActivityForResult(intent, UPDATE_RIDE_ITEM)

    }

    @Subscribe
    fun event(reserveRideEvent: ReserveRideEvent) {
        binding.model!!.reserveRide(
            rideId = reserveRideEvent.rideId,
            numberOfFreeSeats = reserveRideEvent.numberOfFreeSeats,
            passangers = reserveRideEvent.passangers
        )
    }

    @Subscribe
    fun event(cancelReservationEvent: CancelReservationEvent) {
        binding.model!!.cancelRide(
            rideId = cancelReservationEvent.rideId,
            numberOfFreeSeats = cancelReservationEvent.numberOfFreeSeats,
            passangers = cancelReservationEvent.passangers
        )
    }


    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }

    @Subscribe
    fun event(googleMapsLocationEvent: GoogleMapsLocationEvent) {
        typeToFillLocation = googleMapsLocationEvent.type
        openGoogleMapsLocationSuggest()
    }

    fun openGoogleMapsLocationSuggest() {
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        ).build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                when (typeToFillLocation) {
                    "departure" -> {
                        binding.departureEditText.setText(place.name)

                    }
                    "arrival" -> {
                        binding.arrivalEditText.setText(place.name)

                    }

                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                Log.i("GOOGLEMAPS", status.statusMessage)
            }
        } else if (requestCode == UPDATE_RIDE_ITEM) {
            if (resultCode == Activity.RESULT_OK) {
                val newText = data!!.getStringExtra("Reservation")
                Log.d("YESYES", newText)
                val arrayText = newText.split(" ")
                val rideId = arrayText[0]
                val numberOfSeats = arrayText[1].toInt()
                val text = arrayText[2]
                adapter.updateTextAndNumberOfSeats(rideId, numberOfSeats, text)
            }
        }
    }
}