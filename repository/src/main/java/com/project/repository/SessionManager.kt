package com.project.repository

import com.project.models.security.LoginData
import com.project.models.users.User

object SessionManager {
    var loginData: LoginData? = null
    var currentUser: User? = null
    var accessToken: String? = null
    var refreshToken: String? = null
}