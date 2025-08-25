package com.project.models.security
import com.project.models.users.User
interface IdentityService {
    fun userExists(username: String): Boolean
    fun authenticate(email: String, password: String): Boolean
    fun getUser(email: String): User?
}
