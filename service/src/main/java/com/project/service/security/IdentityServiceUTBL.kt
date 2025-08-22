package com.project.service.security

import android.os.Build
import androidx.annotation.RequiresApi
import com.project.repository.security.IdentityRepositoryImpl
import com.project.models.security.IdentityService
import com.project.models.users.User

class IdentityServiceUTBL : IdentityService {
    override fun userExists(username: String): Boolean {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun authenticate(username: String, password: String): Boolean {
        val userRepository = IdentityRepositoryImpl()
        val apiResponse = userRepository.getToken(username, password)
        return apiResponse!!.isNotEmpty()
    }

    override fun getUser(username: String): User? {
        TODO("Not yet implemented")
    }

    override fun biometricRegistered(username: String, password: String): String {
        TODO("Not yet implemented")
    }

    override fun passwordRecovery(
        username: String,
        dateOfBirth: String,
        mobileNumber: String,
        email: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun registered(
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
