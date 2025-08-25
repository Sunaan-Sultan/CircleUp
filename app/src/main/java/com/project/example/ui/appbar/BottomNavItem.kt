package com.project.example.ui.appbar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.example.PreferencesManager
import com.project.example.ui.favourites.Favourites
import com.project.example.ui.home.DisplayPosts
import com.project.example.ui.login.LoginScreen
import com.project.example.ui.profile.ProfileScreen
import com.project.example.ui.registration.RegistrationScreen

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home    : BottomNavItem("home", "Home", Icons.Filled.Home)
    object Favourites : BottomNavItem("favourites","Favourites", Icons.Filled.Favorite)
    object Profile : BottomNavItem("profile", "Profile", Icons.Filled.Person)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    startDestination: String,
    preferencesManager: PreferencesManager,
    context: Context? = null
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
            DisplayPosts(context = context)
        }

        composable("favourites") {
            if (context != null) {
                Favourites(context = context)
            } else {
                Text("Context not available")
            }
        }

        composable("profile"){
            ProfileScreen(navController)
        }

        composable("registration") {
            RegistrationScreen(navController)
        }
    }
}
