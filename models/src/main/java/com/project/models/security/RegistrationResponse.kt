package com.project.models.security

data class RegistrationResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: Any?
)
