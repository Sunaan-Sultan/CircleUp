package com.project.example.ui.registration

object RegistrationValidator {
    private const val MAX_EMAIL_LENGTH = 40
    private const val MAX_PASSWORD_LENGTH = 20

    private val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
    private val passwordRegex = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-_]).{8,}\$")

    fun validateRegistration(
        email: String,
        password: String,
        confirmPassword: String
    ): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error("Email field is required")
            !emailRegex.matches(email) -> ValidationResult.Error("Enter valid email")
            password.isBlank() -> ValidationResult.Error("Password field is required")
            !passwordRegex.matches(password) -> ValidationResult.Error(
                "Password must be at least 8 characters long and include uppercase letter, lowercase letter, number, and special character"
            )
            confirmPassword.isBlank() -> ValidationResult.Error("Confirm Password field is empty")
            password != confirmPassword -> ValidationResult.Error("Passwords do not match")
            else -> ValidationResult.Success
        }
    }

    fun isWithinEmailLimit(text: String): Boolean = text.length <= MAX_EMAIL_LENGTH
    fun isWithinPasswordLimit(text: String): Boolean = text.length <= MAX_PASSWORD_LENGTH
}