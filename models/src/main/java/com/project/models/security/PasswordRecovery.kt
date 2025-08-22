package com.project.models.security

data class PasswordRecovery(
    val userName: String,
    val dateOfBirth: String,
    val mobileNumber: String,
    val email: String,
)
