package com.project.models.security

import com.project.models.users.User

interface IdentityRepository {
    fun userExists(username: String): Boolean
    fun getToken(username: String, password: String): String
    fun getUser(username: String): User?
}
