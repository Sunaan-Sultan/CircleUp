package com.project.example.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.project.example.PreferencesManager
import com.project.example.ui.theme.AppTheme
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.Orientation
import com.project.example.ui.theme.rememberWindowSizeClass
import com.project.models.users.User
import com.project.service.security.SecurityFactory

// Sealed class to represent different authentication results
sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    object InvalidUsername : AuthResult()
    object InvalidPassword : AuthResult()
}

// Presenter class for handling login functionality
class LoginPresenter(private val context: Context) {
    /**
     * Performs login authentication.
     *
     * @param context The context.
     * @param username The provided username.
     * @param password The provided password.
     * @return An instance of AuthResult representing the authentication result.
     */
    fun login(email: String, password: String): AuthResult {
        val identityService = SecurityFactory.getIdentityService(context, "KEY")
        return if (identityService.authenticate(email, password)) {
            val user = identityService.getUser(email)
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.InvalidUsername
            }
        } else {
            if (!identityService.userExists(email)) {
                AuthResult.InvalidUsername
            } else {
                AuthResult.InvalidPassword
            }
        }
    }

}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController, preferencesManager: PreferencesManager) {
    val window = rememberWindowSizeClass()
    CircleUpTheme(window) {
        if (AppTheme.orientation == Orientation.Portrait) {
            LoginView(navController, preferencesManager)
        }
    }
}