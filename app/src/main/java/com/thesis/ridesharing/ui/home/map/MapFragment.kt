package com.thesis.ridesharing.ui.home.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.MapFragmentBinding
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.RidesPinEvent
import com.thesis.ridesharing.ui.rides.ride_detail.RideDetailActivity
import kotlinx.android.synthetic.main.map_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MapFragment : Fragment(), OnMapReadyCallback {


    lateinit var mMap: GoogleMap
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    protected var mLastLocation: Location? = null
    lateinit var binding: MapFragmentBinding

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))
        if (checkPermissions()) {
            mMap.isMyLocationEnabled = true
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
            getLastLocation()
        }
        mMap.setOnInfoWindowClickListener {
            EventBus.getDefault()
                .post(OpenActivityEvent(RideDetailActivity(), objToPass = it.tag.toString()))
        }
        binding.model = MapModel()


    }

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.map_fragment,container,false)
        return binding.root
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
    fun event(ridesPinEvent: RidesPinEvent) {
        val icon = BitmapDescriptorFactory.fromResource(R.mipmap.car)
        if (::mMap.isInitialized) {
            for (ride in ridesPinEvent.rides) {
                val marker = MarkerOptions().position(
                    LatLng(ride.departureLatLng.lat, ride.departureLatLng.lng)
                ).icon(icon).title(ride.departureAndArrival)
                mMap.addMarker(marker).tag = ride.id

            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(null)
        map.onResume()
        map.getMapAsync(this)

    }

    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                mLastLocation = it.result
                setMyLocationInMap(LatLng(mLastLocation!!.latitude, mLastLocation!!.longitude))

            } else {
                Log.w("LOCATION", "getLastLocation:exception", it.exception)
            }

        }

    }

    private fun setMyLocationInMap(latlng: LatLng) {
        val myPosition = CameraPosition.Builder()
            .target(latlng).zoom(7f).bearing(90f).tilt(60f).build()
        mMap.animateCamera(
            CameraUpdateFactory.newCameraPosition(myPosition)
        )


    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

}