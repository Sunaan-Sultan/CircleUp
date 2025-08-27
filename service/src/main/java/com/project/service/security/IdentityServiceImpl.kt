package com.project.service.security

import android.content.Context
import com.project.models.security.IdentityService
import com.project.models.users.User

class IdentityServiceImpl(private val context: Context) : IdentityService {
    override fun authenticate(email: String, password: String): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        val identityResponse = identityRepository.getToken(email, password)
        return identityResponse.isNotEmpty()
    }

    override fun getUser(username: String): User? {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.getUser(username)
    }

    override fun userExists(username: String): Boolean {
        val identityRepository = SecurityFactory.getIdentityRepository(context)
        return identityRepository.userExists(username)
    }
}

