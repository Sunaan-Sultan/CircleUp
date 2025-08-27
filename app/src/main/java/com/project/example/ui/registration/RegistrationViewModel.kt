package com.project.example.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        if (RegistrationValidator.isWithinEmailLimit(email)) {
            _uiState.value = _uiState.value.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) {
        if (RegistrationValidator.isWithinPasswordLimit(password)) {
            _uiState.value = _uiState.value.copy(password = password)
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        if (RegistrationValidator.isWithinPasswordLimit(confirmPassword)) {
            _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
        }
    }

    fun onPasswordVisibilityToggled() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun onConfirmPasswordVisibilityToggled() {
        _uiState.value = _uiState.value.copy(
            isConfirmPasswordVisible = !_uiState.value.isConfirmPasswordVisible
        )
    }

    fun onSignUpClicked() {
        val currentState = _uiState.value
        val validationResult = RegistrationValidator.validateRegistration(
            currentState.email,
            currentState.password,
            currentState.confirmPassword
        )

        _uiState.value = currentState.copy(
            validationResult = validationResult,
            showDialog = true
        )

        if (validationResult is ValidationResult.Success) {
            performRegistration()
        }
    }

    private fun performRegistration() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                delay(2000)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    validationResult = ValidationResult.Success
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    validationResult = ValidationResult.Error("Registration failed. Please try again.")
                )
            }
        }
    }

    fun onDialogDismissed() {
        _uiState.value = _uiState.value.copy(
            showDialog = false,
            validationResult = ValidationResult.None
        )
    }

}