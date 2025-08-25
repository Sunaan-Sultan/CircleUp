package com.project.example.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.example.ui.appbar.AppBar
import com.project.example.ui.theme.BackgroundColor
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.rememberWindowSizeClass

@Composable
fun RegistrationScreen(navController: NavHostController) {
    val onProfileClick: () -> Unit = {
    }
    val context = LocalContext.current
    val currentRoute =
        remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
    Column {
        AppBar(
            navController = navController,
            showCartIcon = false,
            showBackButton = true,
            title = "Registration",
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) BackgroundColor else Color.White)
                .padding(bottom = 40.dp),
        ) {
            val window = rememberWindowSizeClass()
            CircleUpTheme(window) {
                RegistrationView(navController)
            }
        }
    }
}
