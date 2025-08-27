package com.project.repository.security

import com.project.models.security.IdentityRepository
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.models.users.User

/**
 * This class is for local repository implementation
 */

class IdentityLocalRepositoryImpl(context: Context) : IdentityRepository {

    private val users: List<User>


    init {
        users = try {
            val packageName = context.packageName
            val resourceId = context.resources.getIdentifier("users", "raw", packageName)
            val jsonString = context.resources.openRawResource(resourceId)
                .bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<User>>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: Exception) {
            Log.e("IdentityLocalRepo", "Error loading users.json from res/raw", e)
            emptyList()
        }
    }

    override fun userExists(email: String): Boolean {
        return users.any { it.email == email }
    }

    override fun getToken(email: String, password: String): String {
        val user = users.find { it.email == email }
        return if (user != null && user.password == password) {
            "valid_token_for_${user.email}"
        } else {
            ""
        }
    }

    override fun getUser(email: String): User? {
        return users.find { it.email == email }
    }
}
