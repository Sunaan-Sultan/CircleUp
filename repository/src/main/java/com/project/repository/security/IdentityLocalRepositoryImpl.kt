package com.project.repository.security

import com.project.models.security.Identity
import com.project.models.security.IdentityRepository
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.models.users.User

class IdentityLocalRepositoryImpl(context: Context) : IdentityRepository {

    // Load the list of users from JSON stored in assets
    private val users: List<User>


    init {
        users = try {
            // Open the file from res/raw using its resource ID (R.raw.users)
            val packageName = context.packageName
            val resourceId = context.resources.getIdentifier("users", "raw", packageName)
            val jsonString = context.resources.openRawResource(resourceId)
                .bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<User>>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: Exception) {
            Log.e("IdentityLocalRepo", "Error loading users.json from res/raw", e)
            emptyList() // Return an empty list if reading/parsing fails
        }
    }

//    private val identityList = listOf(
//        Identity("medico", "Medi@123"),
//    )

    override fun userExists(email: String): Boolean {
        return users.any { it.username == email }
    }

    override fun getToken(username: String, password: String): String {
        val user = users.find { it.username == username }
        return if (user != null && user.password == password) {
            // return a dummy string as token.
            "valid_token_for_${user.username}"
        } else {
            ""
        }
    }

    override fun getUser(email: String): User? {
        return users.find { it.username == email }
    }

    override fun getBiometricRegistrationToken(username: String, password: String): String {
        val user = users.find { it.username == username }
        return if (user != null && user.password == password) {
            "okay"
        } else {
            ""
        }
    }

    override fun getPasswordRecoveryToken(
        username: String,
        dateOfBirth: String,
        mobileNumber: String,
        email: String,
    ): Boolean {
        return true
    }

    override fun getRegistrationToken(
        username: String,
        firstname: String,
        lastname: String,
        email: String,
        mobileNumber: String,
        gid: String,
        dob: String,
        password: String,
        confirmPassword: String,
    ): String {
        return "hello"
    }
}
