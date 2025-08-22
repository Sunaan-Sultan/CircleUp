package com.project.example

import android.content.Context
import android.content.SharedPreferences

open class PreferencesManager(context: Context) {
    private val     sharedPreferences: SharedPreferences =
        context.getSharedPreferences("RegistrationKey", Context.MODE_PRIVATE)
    private val userPreference: SharedPreferences =
        context.getSharedPreferences("Email", Context.MODE_PRIVATE)
    private val passwordPreference: SharedPreferences =
        context.getSharedPreferences("Password", Context.MODE_PRIVATE)
    fun saveKey(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getKey(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
    fun removeKey(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
    fun saveUsername(key: String, value: String) {
        val editor = userPreference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getUsername(key: String, defaultValue: String): String {
        return userPreference.getString(key, defaultValue) ?: defaultValue
    }
    fun removeUsername(key: String) {
        val editor = userPreference.edit()
        editor.remove(key)
        editor.apply()
    }
    fun savePassword(key: String, value: String) {
        val editor = passwordPreference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getPassword(key: String, defaultValue: String): String {
        return passwordPreference.getString(key, defaultValue) ?: defaultValue
    }
    fun removePassword(key: String) {
        val editor = passwordPreference.edit()
        editor.remove(key)
        editor.apply()
    }
}