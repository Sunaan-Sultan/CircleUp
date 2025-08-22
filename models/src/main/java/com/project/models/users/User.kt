package com.project.models.users

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val isMember: Boolean,
    val userRole: String
)

