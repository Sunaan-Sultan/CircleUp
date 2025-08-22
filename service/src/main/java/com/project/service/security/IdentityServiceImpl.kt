package com.project.service.security

import android.content.Context
import com.project.models.security.IdentityService
import com.project.models.users.User

class IdentityServiceImpl(private val context: Context) : IdentityService {
    override fun authenticate(username: String, password: String): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        val identityResponse = identityRepository.getToken(username, password)
        return identityResponse.isNotEmpty()
    }

    override fun getUser(username: String): User? {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.getUser(username)
    }

    override fun biometricRegistered(username: String, password: String): String {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.getBiometricRegistrationToken(username, password)
    }
    override fun passwordRecovery(
        username: String,
        dateOfBirth: String,
        mobileNumber: String,
        email: String,
    ): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.getPasswordRecoveryToken(username, dateOfBirth, mobileNumber, email)
    }

    override fun registered(
        username: String,
        firstname: String,
        lastname: String,
        email: String,
        mobileNumber: String,
        gid: String,
        dob: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        val identityResponse = identityRepository.getRegistrationToken(
            username,
            firstname,
            lastname,
            email,
            mobileNumber,
            gid,
            dob,
            password,
            confirmPassword
        )
        return identityResponse.isNotEmpty()
    }


    override fun userExists(username: String): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.userExists(username)
    }
}

