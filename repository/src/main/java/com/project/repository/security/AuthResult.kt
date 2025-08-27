package com.project.repository.security

import com.project.models.users.User

sealed class AuthResult {
    data class Success(val user: User, val token: String) : AuthResult()
    object InvalidCredentials : AuthResult()
    object UserNotFound : AuthResult()
    data class Error(val message: String) : AuthResult()
}