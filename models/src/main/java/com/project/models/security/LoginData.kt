package com.project.models.security

data class LoginData(
    val id: Int,
    val email: String,
    val role: String,
    val isActive: Int
)
