package com.project.service.security

import android.content.Context
import com.project.models.users.User
import com.project.repository.security.AuthResult
import com.project.repository.security.SessionManager

class AuthenticationService(private val context: Context) {

    private val identityService = SecurityFactory.getIdentityService(context, "KEY")

    fun login(email: String, password: String): AuthResult {
        return try {

            if (!identityService.userExists(email)) {
                return AuthResult.UserNotFound
            }

            if (!identityService.authenticate(email, password)) {
                return AuthResult.InvalidCredentials
            }

            val user = identityService.getUser(email)
            if (user == null) {
                return AuthResult.Error("Failed to retrieve user details")
            }

            val identityRepository = SecurityFactory.getIdentityRepository(context)
            val token = identityRepository.getToken(email, password)

            SessionManager.apply {
                currentUser = user
                accessToken = token
                loginData = mapUserToLoginData(user)
            }

            AuthResult.Success(user, token)

        } catch (e: Exception) {
            AuthResult.Error("Authentication failed: ${e.message}")
        }
    }

    fun logout() {
        SessionManager.apply {
            currentUser = null
            accessToken = null
            refreshToken = null
            loginData = null
        }
    }

    private fun mapUserToLoginData(user: User): com.project.models.security.LoginData {
        return com.project.models.security.LoginData(
            id = user.id,
            email = user.email,
            role = user.userRole,
            isActive = 1
        )
    }
}