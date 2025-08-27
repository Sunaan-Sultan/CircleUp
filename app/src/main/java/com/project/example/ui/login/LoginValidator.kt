package com.project.example.ui.login

object LoginValidator {
    private const val MAX_EMAIL_LENGTH = 40
    private const val MAX_PASSWORD_LENGTH = 20

    private val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")

    fun validateLogin(email: String, password: String): String? {
        return when {
            email.isBlank() -> "Email field is required"
            !emailRegex.matches(email) -> "Enter valid email"
            password.isBlank() -> "Password field is required"
            else -> null
        }
    }

    fun isWithinEmailLimit(text: String): Boolean = text.length <= MAX_EMAIL_LENGTH
    fun isWithinPasswordLimit(text: String): Boolean = text.length <= MAX_PASSWORD_LENGTH
}