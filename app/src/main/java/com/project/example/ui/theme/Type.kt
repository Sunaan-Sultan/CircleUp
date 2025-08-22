package com.project.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.project.repository.R

// Define your custom font family
val capitaFontFamily = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_light, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
)

// Define your base Typography for Material 3
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    // Override other text styles as needed
    bodyMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
    // Add more styles as needed
)

// Define typography for different screen sizes
val typographySmall = Typography(
    bodyLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
    ),
)

val typographyCompact = Typography(
    bodyLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
    ),
)

val typographyMedium = Typography(
    bodyLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 27.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
    ),
)

val typographyBig = Typography(
    bodyLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 29.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 39.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = capitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 50.sp,
    ),
)