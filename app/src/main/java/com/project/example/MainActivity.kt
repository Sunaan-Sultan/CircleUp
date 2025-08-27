package com.project.example

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.example.ui.appbar.AppBar
import com.project.example.ui.appbar.BottomNavigationBar
import com.project.example.ui.appbar.Navigation
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.rememberWindowSizeClass

class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = PreferencesManager(applicationContext)
        val savedUser = prefs.getUsername("user", "")
        val savedPass = prefs.getPassword("pass", "")
        val startDestination = if (savedUser.isNotEmpty() && savedPass.isNotEmpty()) {
            "home"
        } else {
            "login"
        }

        setContent {
            val navController = rememberNavController()

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            val hideAllBars = listOf("login")

            val hideBottomBarOnly = listOf("registration")

            val shouldShowTopBar = currentRoute != null && currentRoute !in hideAllBars
            val shouldShowBottomBar = currentRoute != null &&
                    currentRoute !in hideAllBars &&
                    currentRoute !in hideBottomBarOnly

            val window = rememberWindowSizeClass()

            CircleUpTheme(window) {
                if (shouldShowTopBar) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                title = when (currentRoute) {
                                    "home" -> "Posts"
                                    "profile" -> "Profile"
                                    "posts" -> "Posts"
                                    "favourites" -> "Favorites"
                                    "registration" -> "Sign Up"
                                    else -> "Circle UP"
                                },
                                showCartIcon = false,
                                showBackButton = currentRoute in listOf("registration"),
                                navController = navController
                            )
                        },
                        bottomBar = {
                            if (shouldShowBottomBar) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) { innerPadding ->
                        Navigation(
                            navController = navController,
                            innerPadding = innerPadding,
                            startDestination = startDestination,
                            preferencesManager = prefs,
                            context = this@MainActivity
                        )
                    }
                } else {
                    Navigation(
                        navController = navController,
                        innerPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                        startDestination = startDestination,
                        preferencesManager = prefs,
                        context = this@MainActivity
                    )
                }
            }
        }
    }
}