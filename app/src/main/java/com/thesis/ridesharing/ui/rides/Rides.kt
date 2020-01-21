package com.thesis.ridesharing.ui.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RidesListFragmentBinding

class Rides : Fragment() {
    lateinit var adapter: RideAdapter
    lateinit var binding: RidesListFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_list_fragment, container, false)
        val mLayoutManager = LinearLayoutManager(context)
        binding.ridesRecycleview.layoutManager = mLayoutManager
        adapter = RideAdapter()
        binding.ridesRecycleview.adapter = adapter
        return binding.root
    }
}