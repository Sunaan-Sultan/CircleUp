package com.project.example.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.example.PreferencesManager
import com.project.models.users.User
import com.project.repository.security.AuthResult
import com.project.service.security.AuthenticationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val context: Context,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val authService = AuthenticationService(context)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        loadSavedCredentials()
    }

    private fun loadSavedCredentials() {
        val savedEmail = preferencesManager.getKey("email", "")
        val savedPassword = preferencesManager.getKey("password", "")
        val rememberMe = savedEmail.isNotEmpty() && savedPassword.isNotEmpty()

        _uiState.value = _uiState.value.copy(
            email = savedEmail,
            password = savedPassword,
            isRememberMeChecked = rememberMe
        )
    }

    fun onEmailChanged(email: String) {
        if (email.length <= 40) { // Max email length validation
            _uiState.value = _uiState.value.copy(
                email = email,
                errorMessage = null
            )
        }
    }

    fun onPasswordChanged(password: String) {
        if (password.length <= 20) { // Max password length validation
            _uiState.value = _uiState.value.copy(
                password = password,
                errorMessage = null
            )
        }
    }

    fun onPasswordVisibilityToggled() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun onRememberMeToggled() {
        val newRememberMeState = !_uiState.value.isRememberMeChecked
        _uiState.value = _uiState.value.copy(
            isRememberMeChecked = newRememberMeState
        )

        if (!newRememberMeState) {
            clearSavedCredentials()
        }
    }

    fun onLoginClicked() {
        val currentState = _uiState.value

        // Basic validation
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Email is required")
            return
        }

        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Password is required")
            return
        }

        // Show loading state
        _uiState.value = currentState.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {
            try {
                val authResult = authService.login(currentState.email, currentState.password)
                handleAuthResult(authResult)
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Login failed: ${e.message}"
                )
            }
        }
    }

    private fun handleAuthResult(result: AuthResult) {
        val currentState = _uiState.value

        when (result) {
            is AuthResult.Success -> {
                saveUserSession(result.user, currentState.email, currentState.password)
                _uiState.value = currentState.copy(
                    isLoading = false,
                    isLoginSuccessful = true,
                    errorMessage = null
                )
            }
            is AuthResult.InvalidCredentials -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Invalid email or password"
                )
            }
            is AuthResult.UserNotFound -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "User not found"
                )
            }
            is AuthResult.Error -> {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }

    private fun saveUserSession(user: User, email: String, password: String) {
        // Save session data
        preferencesManager.saveKey("userId", user.id.toString())
        preferencesManager.saveKey("userRole", user.userRole.lowercase())
        preferencesManager.saveUsername("user", email)
        preferencesManager.savePassword("pass", password)

        // Save credentials if remember me is checked
        if (_uiState.value.isRememberMeChecked) {
            preferencesManager.saveKey("email", email)
            preferencesManager.saveKey("password", password)
        }

        Log.d("LOGIN", "User session saved - ID: ${user.id}, Role: ${user.userRole}")
    }

    private fun clearSavedCredentials() {
        preferencesManager.removeKey("email")
        preferencesManager.removeKey("password")
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetLoginState() {
        _uiState.value = _uiState.value.copy(isLoginSuccessful = false)
    }
}