package com.thesis.ridesharing.ui.home.show_rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.RidesFragmentBinding
import com.thesis.ridesharing.ui.rides.RidesFragmentAdapter


class RidesFragment(val type:String="NonArchived") : Fragment() {
    lateinit var binding: RidesFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_fragment, container, false)
        val view_pager_adapter = RidesFragmentAdapter(childFragmentManager, type)
        binding.viewPager.adapter = view_pager_adapter;
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root

    }




}
