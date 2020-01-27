package com.thesis.ridesharing.ui.rides.add_ride

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
import com.thesis.ridesharing.databinding.AddRideActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.GoogleMapsLocationEvent
import com.thesis.ridesharing.events.ShowSeatsForRide
import com.thesis.ridesharing.models.LatLng
import com.thesis.ridesharing.ui.my_vehicles.MyVehiclesAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


class AddRideActivity : Activity() {

    lateinit var binding: AddRideActivityBinding
    lateinit var adapter: MyVehiclesAdapter
    var AUTOCOMPLETE_REQUEST_CODE = 1
    var typeToFillLocation: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_ride_activity)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val apiKey = getString(R.string.google_key_api)

        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey)
        }
        val placesClient = Places.createClient(this)
        binding.myVehiclesRecycleView.layoutManager = mLayoutManager
        adapter = MyVehiclesAdapter(isAddRide = true)
        binding.model = AddRideViewModel(binding, adapter)
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
    fun event(showSeatsForRide: ShowSeatsForRide) {
        binding.model!!.numberOfSeats = showSeatsForRide.numberOfSeats
        binding.freeSeatsDescpEditText.setText(showSeatsForRide.numberOfSeats.toString())
    }

    @Subscribe
    fun event(googleMapsLocationEvent: GoogleMapsLocationEvent) {
        typeToFillLocation = googleMapsLocationEvent.type
        openGoogleMapsLocationSuggest()
    }

    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
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
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                when (typeToFillLocation) {
                    "departure" -> {
                        binding.departureEditText.setText(place.name)
                        binding.model!!.departureLatLng = LatLng(
                            lat = place.latLng!!.latitude,
                            lng = place.latLng!!.longitude
                        )
                    }
                    "arrival" -> {
                        binding.arrivalEditText.setText(place.name)
                        binding.model!!.arrivalLatLng = LatLng(
                            lat = place.latLng!!.latitude,
                            lng = place.latLng!!.longitude
                        )

                    }

                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                Log.i("GOOGLEMAPS", status.statusMessage)
            }
        }
    }

}