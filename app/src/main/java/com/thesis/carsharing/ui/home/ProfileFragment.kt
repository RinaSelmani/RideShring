package com.thesis.carsharing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.carsharing.R
import com.thesis.carsharing.databinding.ProfileFragmentBinding
import com.thesis.carsharing.databinding.RidesListFragmentBinding
import com.thesis.carsharing.ui.rides.RideRecycleViewAdapter

class ProfileFragment : Fragment() {

    lateinit var binding: ProfileFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }

}