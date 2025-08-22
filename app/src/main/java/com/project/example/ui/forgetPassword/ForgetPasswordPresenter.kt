package com.project.example.ui.forgetPassword

import android.annotation.SuppressLint
import android.content.Context
import com.project.service.security.SecurityFactory

class ForgetPasswordPresenter {
    // Function to initiate the password recovery process
    @SuppressLint("NotConstructor")
    fun forgetPasswordPresenter(
        context: Context,
        username: String,
        dateOfBirth: String,
        mobileNumber: String,
        email: String,
    ): Boolean {
        // Obtain an instance of the identity service using the SecurityFactory
        val identityService = SecurityFactory.getIdentityService(context, "KEY")
        // This method is responsible for initiating the password recovery process
        return identityService.passwordRecovery(username, dateOfBirth, mobileNumber, email)
    }
}
