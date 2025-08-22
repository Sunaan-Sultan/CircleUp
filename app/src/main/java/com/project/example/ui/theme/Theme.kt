package com.project.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = White,
    tertiary = White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = Black,
    tertiary = Black

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CircleUpTheme(
    windowSizeClass: WindowSizeClass,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    // Determine the device orientation based on the width and height of the window
    val orientation = when {
        windowSizeClass.width.size > windowSizeClass.height.size -> Orientation.Landscape
        else -> Orientation.Portrait
    }

    // Choose the dimension that matters based on the device orientation
    val sizeThatMatters = when (orientation) {
        Orientation.Portrait -> windowSizeClass.height
        else -> windowSizeClass.width
    }

    // Select dimensions and typography based on the size that matters
    val dimensions = when (sizeThatMatters) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.SemiCompact -> semiCompactDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        else -> largeDimensions
    }

    val typography = when (sizeThatMatters) {
        is WindowSize.Small -> typographySmall
        is WindowSize.SemiCompact -> typographyCompact
        is WindowSize.Compact -> typographyCompact
        is WindowSize.Medium -> typographyMedium
        else -> typographyBig
    }

    ProvideAppUtils(dimensions = dimensions, orientation = orientation) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}

object AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val orientation: Orientation
        @Composable
        get() = LocalOrientationMode.current
}