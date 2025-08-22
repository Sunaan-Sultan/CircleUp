package com.project.example.ui.registration

import android.content.Context
import com.project.service.security.SecurityFactory

class RegistrationPresenter {
    fun registration(context: Context, email: String, password: String, confirmPassword: String): Boolean {

        val identityService = SecurityFactory.getIdentityService(context, "KEY")

        return identityService.registered(email, password, confirmPassword)
    }
}
