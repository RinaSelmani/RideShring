package com.thesis.ridesharing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.thesis.ridesharing.databinding.MainActivityBinding
import com.thesis.ridesharing.events.LogOutEvent
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.models.ReserveRideEvent
import com.thesis.ridesharing.models.User
import com.thesis.ridesharing.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {
    lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation_controller = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigateBottom(navigation_controller)
        getData()


    }


    fun getData() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("USERS").document(uid!!).get().addOnSuccessListener {
            if (it != null) {
                val user = it.toObject(User::class.java)!!
                currentUser = user
            }

        }
            .addOnFailureListener() {
                Log.d("DOCUMENT SNAP", it.localizedMessage)
            }
    }

    private fun navigateBottom(nav_controller: NavController) {
        bottom_navigation.let {
            NavigationUI.setupWithNavController(it, nav_controller)
        }
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
    fun event(logOutEvent: LogOutEvent) {
        val intent = Intent(
            this, LoginActivity()::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    @Subscribe
    fun event(openActivityEvent: OpenActivityEvent) {
        val intent = Intent(this, openActivityEvent.activity::class.java)
        if (openActivityEvent.objToPass != "") {
            intent.putExtra("RIDE", openActivityEvent.objToPass)
        }
        startActivity(intent)
    }



}
