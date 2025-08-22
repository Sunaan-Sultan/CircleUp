package com.project.models.security

data class LoginData(
    val id: Int,
    val username: String,
    val firstname: String,
    val lastname: String,
    val gid: String,
    val dob: String,
    val address: String,
    val createdate: String,
    val updatedate: String,
    val email: String,
    val mobile: String,
    val role: String,
    val isActive: Int
)
