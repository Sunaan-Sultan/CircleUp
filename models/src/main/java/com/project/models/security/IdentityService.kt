package com.project.models.security
import com.project.models.users.User
interface IdentityService {
    fun userExists(username: String): Boolean
    fun authenticate(username: String, password: String): Boolean
    fun getUser(username: String): User?
    fun biometricRegistered(username: String, password: String): String
    fun passwordRecovery(username: String, dateOfBirth: String, mobileNumber: String, email: String): Boolean
    fun registered(username: String, firstname: String, lastname: String, email: String, mobileNumber: String, gid: String, dob: String, password: String, confirmPassword: String): Boolean
}
