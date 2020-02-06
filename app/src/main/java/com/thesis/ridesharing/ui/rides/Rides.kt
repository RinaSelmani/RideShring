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
import com.thesis.ridesharing.events.CancelReservationEvent
import com.thesis.ridesharing.events.DeleteRideEvent
import com.thesis.ridesharing.ui.home.show_rides.ShowRidesViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class Rides(val type: String) : Fragment() {
    lateinit var adapter: RideAdapter
    lateinit var binding: RidesListFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_list_fragment, container, false)
        val mLayoutManager = LinearLayoutManager(context)
        binding.ridesRecycleview.layoutManager = mLayoutManager
        adapter = RideAdapter()
        binding.model= ShowRidesViewModel(binding,adapter,type)
        return binding.root
    }
    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun event(cancelReservationEvent: CancelReservationEvent) {
        binding.model!!.cancelRide(
            rideId = cancelReservationEvent.rideId,
            numberOfFreeSeats = cancelReservationEvent.numberOfFreeSeats,
            passangers = cancelReservationEvent.passangers
        )
    }

    @Subscribe
    fun event(deleteRideEvent: DeleteRideEvent) {
        binding.model!!.deleteRide(deleteRideEvent.rideId)
    }



}