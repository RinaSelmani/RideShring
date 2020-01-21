package com.thesis.ridesharing.ui.home.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MapStyleOptions
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.MapFragmentBinding
import kotlinx.android.synthetic.main.map_fragment.*

class MapFragment : Fragment(), OnMapReadyCallback {
    lateinit var mMap: GoogleMap

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))
    }

    lateinit var binding: MapFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.map_fragment,container,false)
        binding.model= MapModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(null)
        map.onResume()
        map.getMapAsync(this)

    }

}