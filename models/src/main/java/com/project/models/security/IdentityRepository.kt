package com.project.models.security

import com.project.models.users.User

interface IdentityRepository {
    fun userExists(username: String): Boolean
    fun getToken(username: String, password: String): String
    fun getUser(username: String): User?
    fun getBiometricRegistrationToken(username: String, password: String): String
    fun getPasswordRecoveryToken(username: String, dateOfBirth: String, mobileNumber: String, email: String): Boolean
    fun getRegistrationToken(username: String, firstname: String, lastname: String, email: String, mobileNumber: String, gid: String, dob: String, password: String, confirmPassword: String): String
}
