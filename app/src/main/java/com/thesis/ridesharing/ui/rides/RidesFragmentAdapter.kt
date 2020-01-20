package com.thesis.ridesharing.ui.rides

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class RidesFragmentAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    lateinit var fragment: Fragment
    lateinit var title: String
    val FRAGMENT_COUNT: Int = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> fragment = Rides()
            1 -> fragment = Rides()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> title = "Posted Rides"
            1 -> title = "Participated Rides"
        }
        return title
    }

    override fun getCount(): Int {
        return FRAGMENT_COUNT
    }


}