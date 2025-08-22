package com.project.models.security

data class RegistrationRequest(
    val username: String,
    val firstname: String,
    val lastname: String,
    val gid: String = "",
    val dob: String = "",
    val address: String = "",
    val createdate: String = "",
    val updatedate: String = "",
    val email: String,
    val mobile: String,
    val password: String,
    val role: String = "member",
    val isActive: Int = 0
)
