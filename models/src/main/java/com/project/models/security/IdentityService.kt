package com.project.models.security
import com.project.models.users.User
interface IdentityService {
    fun userExists(username: String): Boolean
    fun authenticate(email: String, password: String): Boolean
    fun getUser(email: String): User?
    fun biometricRegistered(username: String, password: String): String
    fun passwordRecovery(username: String, dateOfBirth: String, mobileNumber: String, email: String): Boolean
    fun registered(email: String, password: String, confirmPassword: String): Boolean
}
