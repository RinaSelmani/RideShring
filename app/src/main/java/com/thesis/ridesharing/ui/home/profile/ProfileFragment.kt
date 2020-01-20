package com.thesis.ridesharing.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.thesis.ridesharing.R
import com.thesis.ridesharing.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    lateinit var binding: ProfileFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        binding.model=ProfileViewModel(binding)
        return binding.root
    }

}
