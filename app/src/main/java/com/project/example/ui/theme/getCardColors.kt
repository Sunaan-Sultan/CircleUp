package com.project.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getCardColors(): Pair<Color, Color> {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) CardColor else Color.Companion.White
    val contentColor = if (isDarkTheme) Color.Companion.White else Color.Companion.Black
    return Pair(backgroundColor, contentColor)
}
