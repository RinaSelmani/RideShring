package com.thesis.ridesharing.ui.add_car

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.thesis.ridesharing.R
import com.thesis.ridesharing.car_brands
import com.thesis.ridesharing.colors
import com.thesis.ridesharing.databinding.AddVehicleActivityBinding
import com.thesis.ridesharing.events.CloseActivityEvent
import com.thesis.ridesharing.events.OpenDialogEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddVehicleActivity : AppCompatActivity() {
    lateinit var binding: AddVehicleActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.add_vehicle_activity)
        binding.model = AddVehicleModel(binding)
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
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }

    @Subscribe
    fun event(dialog: OpenDialogEvent) {
        showDialog(dialog.type)
    }

    fun showDialog(type: String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogCustom)
        builder.setTitle("Choose " + type)
        var items = emptyArray<String>()
        when (type) {
            "Brand" -> {
                items = car_brands.keys.toTypedArray()
            }
            "Model" -> {
                val brand = binding.brandEditText.text.toString()
                if (!brand.equals("Brand")) {
                    items = car_brands[brand]!!
                } else {
                }

            }
            "Color" -> {
                items = colors
            }
        }

        val checkedItem = 1 // cow
        builder.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            // user checked an item
        }


        builder.setPositiveButton("OK") { dialog, which ->
            when (type) {
                "Brand" -> {
                    //Log.println(which)
                    //binding.brandEditText.setText(items[which])
                }
                "Model" -> {
                    binding.modelEditText.setText(items[which])

                }
                "Color" -> {
                    binding.colorEditText.setText(items[which])

                }
            }
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}