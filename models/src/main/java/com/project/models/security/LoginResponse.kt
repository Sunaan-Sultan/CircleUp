package com.project.models.security

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val success: Boolean,
    val statusCode: Int,
    val message: String,
    val data: LoginData?,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)
