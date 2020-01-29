package com.thesis.ridesharing.ui.rides

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class RidesFragmentAdapter internal constructor(fm: FragmentManager, val type: String) :
    FragmentPagerAdapter(fm) {
    lateinit var fragment: Fragment
    lateinit var title: String
    val FRAGMENT_COUNT: Int = 2

    override fun getItem(position: Int): Fragment {
        var firstFragmentType = ""
        var secondFragmentType = ""
        when (type) {
            "Archived" -> {
                firstFragmentType = "archivedPostedByMe"
                secondFragmentType = "archivedParticipated"

            }
            "NonArchived" -> {
                firstFragmentType = "postedByMe"
                secondFragmentType = "participated"
            }
        }
        when (position) {
            0 -> fragment = Rides(firstFragmentType)
            1 -> fragment = Rides(secondFragmentType)
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