package com.project.example.ui.appbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.example.PreferencesManager
import com.project.example.ui.forgetPassword.ForgetPasswordScreen
import com.project.example.ui.home.HomeScreen
import com.project.example.ui.login.LoginScreen
import com.project.example.ui.profile.BioDataScreen
import com.project.example.ui.profile.ProfileScreen
import com.project.example.ui.profile.ProfileView
import com.project.example.ui.registration.RegistrationScreen

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home    : BottomNavItem("home",    "Home",    Icons.Filled.Home)
    object Product : BottomNavItem("products","Product", Icons.Filled.ShoppingCart)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String,
    preferencesManager: PreferencesManager,
) {
    NavHost(
        navController    = navController,
        startDestination = startDestination,
        modifier         = Modifier.padding(innerPadding)
    ) {
        composable("login") {
            LoginScreen(navController, preferencesManager)
        }
        composable("home") {
            HomeScreen(context = LocalContext.current)
        }

        // Profile & misc
        composable("profile")      { ProfileScreen(navController) }
        composable("bio")          { BioDataScreen(navController) }
        composable("profile-info") { ProfileView(navController) }

        composable("registration") {
            RegistrationScreen(navController)
        }

        composable("forgetPassword") {
            ForgetPasswordScreen(navController)
        }
    }
}
