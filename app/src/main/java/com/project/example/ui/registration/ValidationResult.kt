package com.project.example.ui.registration

sealed class ValidationResult {
    object None : ValidationResult()
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}