package com.project.example.ui.registration

import android.content.Context
import com.project.service.security.SecurityFactory

class RegistrationPresenter {
    fun registration(context: Context, username: String, firstname: String, lastname: String, email: String, mobileNumber: String, gid: String, dob: String, password: String, confirmPassword: String): Boolean {

        val identityService = SecurityFactory.getIdentityService(context, "KEY")

        return identityService.registered(username, firstname, lastname, email, mobileNumber, gid, dob, password, confirmPassword)
    }
}
