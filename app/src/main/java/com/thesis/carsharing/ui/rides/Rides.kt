package com.thesis.carsharing.ui.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.RidesListFragmentBinding

class Rides : Fragment() {
    lateinit var adapter: RideRecycleViewAdapter
    lateinit var binding: RidesListFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_list_fragment, container, false)
        adapter = RideRecycleViewAdapter()
        binding.ridesRecycleview.adapter = adapter
        return binding.root
    }
}