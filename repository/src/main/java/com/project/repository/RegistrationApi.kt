package com.project.repository

import com.project.models.security.RegistrationRequest
import com.project.models.security.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RegistrationApi {
    @POST("users")
    suspend fun registerUser(
        @Header("Authorization") authorization: String = "Bearer use_your_simple_valid_token",
        @Body registrationRequest: RegistrationRequest
    ): Response<RegistrationResponse>
}

