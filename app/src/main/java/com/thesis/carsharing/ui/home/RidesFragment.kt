package com.thesis.carsharing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.RidesFragmentBinding
import com.thesis.carsharing.ui.rides.RidesFragmentAdapter

class RidesFragment : Fragment() {
    lateinit var fragmentAdapter: RidesFragmentAdapter
    lateinit var binding: RidesFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_fragment, container, false)
        if (fragmentManager != null) {
            fragmentAdapter = RidesFragmentAdapter(fragmentManager!!)
            binding.pager.adapter = fragmentAdapter
            binding.tabLayout.setupWithViewPager(binding.pager)
        }
        return binding.root

    }
}
