package com.project.example.ui.registration

data class RegistrationUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val validationResult: ValidationResult = ValidationResult.None
)
