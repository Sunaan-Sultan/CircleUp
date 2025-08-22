package com.project.example.ui.forgetPassword

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.example.ui.appbar.AppBar
import com.project.example.ui.theme.BackgroundColor
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.rememberWindowSizeClass

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForgetPasswordScreen(navController: NavHostController) {
    val currentRoute = remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
    Column {
        AppBar(
            navController = navController,
            showCartIcon = false,
            showBackButton = true,
            title = "Password Recovery",
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) BackgroundColor else Color.White)
                .padding(bottom = 40.dp),
        ) {
            val window = rememberWindowSizeClass()
            CircleUpTheme(window) {
                ForgetPasswordView(navController)
            }
        }
    }
}
