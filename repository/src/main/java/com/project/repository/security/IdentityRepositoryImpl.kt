package com.project.repository.security

import com.project.models.security.IdentityRepository
import com.project.models.users.User

/**
 * This class is for live repository implementation
 */

class IdentityRepositoryImpl : IdentityRepository {
    override fun userExists(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getToken(username: String, password: String): String {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): User? {
        TODO("Not yet implemented")
    }

}
