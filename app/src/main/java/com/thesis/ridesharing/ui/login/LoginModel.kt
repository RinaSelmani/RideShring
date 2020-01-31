package com.thesis.ridesharing.ui.login

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thesis.ridesharing.MainActivity
import com.thesis.ridesharing.databinding.LoginActivityBinding
import com.thesis.ridesharing.email_pattern
import com.thesis.ridesharing.events.OpenActivityEvent
import com.thesis.ridesharing.ui.register.RegisterActivity
import com.thesis.ridesharing.ui.reset_password.ResetPasswordActivity
import org.greenrobot.eventbus.EventBus

class
LoginModel(val binding: LoginActivityBinding) {
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    val LOGIN_ERORR = "LOGIN ERROR"

    init {
        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()
    }
    fun login() {
        binding.progressBarHolder.visibility = View.VISIBLE
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()


        if (validateData(email, password)) {
            mAuth!!.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                EventBus.getDefault().post(OpenActivityEvent(MainActivity()))
            }
                .addOnFailureListener() {
                    Toast.makeText(
                        binding.root.context,
                        "Please Check your login info",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progressBarHolder.visibility = View.GONE
                    Log.d(LOGIN_ERORR,it.localizedMessage)
                }

        }


    }

    fun validateData(email: String, password: String): Boolean {
        if (!email.equals("") and !password.equals("")) {
            if (email.matches(email_pattern.toRegex())) {
                return true
            }

        }
        binding.progressBarHolder.visibility = View.GONE
        Toast.makeText(binding.root.context, "Please check your log in data!", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    fun openRegisterActivity() {
        EventBus.getDefault().post(OpenActivityEvent(RegisterActivity()))

    }

    fun openResetLinkActivity() {
        EventBus.getDefault().post(OpenActivityEvent(ResetPasswordActivity()))

    }

}