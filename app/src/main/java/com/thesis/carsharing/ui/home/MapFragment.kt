package com.thesis.carsharing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.MapFragmentBinding

class MapFragment : Fragment() {

    lateinit var binding: MapFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)

        return binding.root
    }
}