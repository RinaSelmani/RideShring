package com.thesis.ridesharing.ui.reset_password

import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.thesis.ridesharing.databinding.ResetPasswordActivityBinding
import com.thesis.ridesharing.email_pattern
import com.thesis.ridesharing.events.CloseActivityEvent
import org.greenrobot.eventbus.EventBus


class ResetPasswordViewModel(val binding: ResetPasswordActivityBinding) {
    private var firestoreDb: FirebaseAuth

    init {
        firestoreDb = FirebaseAuth.getInstance()

    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())

    }

    fun sendLink() {
        val email = binding.emailEditText.text.toString()
        if (validateEmail(email)) {
            firestoreDb.sendPasswordResetEmail(email)
                .addOnCompleteListener(OnCompleteListener<Void> { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            binding.root.context,
                            "An email with reset link was sent to your email," +
                                    "please reset password and try again to log in",
                            Toast.LENGTH_LONG
                        ).show()
                        EventBus.getDefault().post(CloseActivityEvent())
                    }
                })
        }

    }

    fun validateEmail(email: String): Boolean {
        if (email.matches(email_pattern.toRegex())) {
            return true
        }

        Toast.makeText(binding.root.context, "Check you email please", Toast.LENGTH_SHORT).show()
        return false
    }


}