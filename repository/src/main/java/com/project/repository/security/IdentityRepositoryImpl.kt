package com.project.repository.security

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.project.models.security.IdentityRepository
import com.project.models.security.LoginRequest
import com.project.models.security.RegistrationRequest
import com.project.models.users.User
import com.project.repository.RetrofitClient
import com.project.repository.SessionManager
import io.ktor.util.InternalAPI
import kotlinx.coroutines.runBlocking

var authToken: String? = null
class IdentityRepositoryImpl : IdentityRepository {
    private var loggedInUser: User? = null

    override fun userExists(email: String): Boolean {
        return SessionManager.currentUser?.email.equals(email, ignoreCase = true)
    }

    override fun getUser(email: String): User? {
        return return SessionManager.currentUser
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(InternalAPI::class)
    override fun getToken(email: String, password: String): String {
        var token = ""
        runBlocking {
            try {
                val req = LoginRequest(email = email, password = password)
                val resp = RetrofitClient.loginApi.loginUser(req)
                Log.d("IdentityRepo", "Login response: $resp")

                if (resp.isSuccessful) {
                    val body = resp.body()
                    Log.d("IdentityRepo", "Login response body: $body")
                    if (body != null && body.success && body.data != null) {
                        // 1) cache user
                        val d = body.data
                        if (d != null) {
                            SessionManager.currentUser = User(
                                id = d.id,
                                username = d.username,
                                email = d.email,
                                password = password,
                                isMember = d.role.equals("member", ignoreCase = true),
                                userRole = d.role
                            )
                            Log.d("IdentityRepo", "User cached: ${SessionManager.currentUser}")
                        }

                        // 2) cache tokens
                        SessionManager.loginData = body.data
                        SessionManager.accessToken = body.accessToken
                        SessionManager.refreshToken = body.refreshToken

                        // 3) return accessToken as “token”
                        token = body.accessToken.orEmpty()
                        Log.d("IdentityRepo", "Login successful: $token")
                    } else {
                        Log.e("IdentityRepo", "Login failed: ${body?.message}")
                    }
                } else {
                    Log.e("IdentityRepo", "Login HTTP ${resp.code()} — ${resp.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("IdentityRepo", "Exception during login", e)
            }
        }
        return token
    }

    override fun getBiometricRegistrationToken(username: String, password: String): String {
        TODO("Not yet implemented")
    }

    override fun getPasswordRecoveryToken(
        username: String,
        dateOfBirth: String,
        mobileNumber: String,
        email: String,
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRegistrationToken(
        email: String,
        password: String,
        confirmPassword: String
    ): String {
        if (password != confirmPassword) {
            return ""
        }
        var registrationMessage = ""
        runBlocking {
            try {
                val registrationRequest = RegistrationRequest(
                    email = email,
                    password = password
                )
                val response = RetrofitClient.registrationApi.registerUser(registrationRequest = registrationRequest)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        registrationMessage = body.messages.firstOrNull() ?: "Registration successful"
                        Log.d("Registration", "Registration successful: $registrationMessage")
                    } else {
                        Log.e("Registration", "API responded with unsuccessful result")
                    }
                } else {
                    Log.e("Registration", "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Registration", "Exception during registration: ${e.message}")
            }
        }
        return registrationMessage
    }


}
