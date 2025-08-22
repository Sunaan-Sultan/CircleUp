package com.project.example.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen(
    context: Context
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp )
    ) {

    }
}