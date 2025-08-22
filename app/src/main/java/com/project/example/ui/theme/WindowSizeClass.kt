package com.project.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

// sealed class to represent different window sizes
sealed class WindowSize(val size: Int) {

    data class Small(val smallSize: Int) : WindowSize(smallSize)
    data class SemiCompact(val semiCompactSize: Int) : WindowSize(semiCompactSize)
    data class Compact(val compactSize: Int) : WindowSize(compactSize)
    data class Medium(val mediumSize: Int) : WindowSize(mediumSize)
    data class Large(val largeSize: Int) : WindowSize(largeSize)
}

// Data class to represent the size of the window in both width and height
data class WindowSizeClass(
    val width: WindowSize,
    val height: WindowSize,
)

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val config = LocalConfiguration.current

    val width by remember(config) {
        mutableStateOf(config.screenWidthDp)
    }

    val height by remember(config) {
        mutableStateOf(config.screenHeightDp)
    }

    // Determine the window width class based on the width
    val windowWidthClass = when {
        width <= 360 -> WindowSize.Small(width)
        width in 361..480 -> WindowSize.Compact(width)
        width in 481..720 -> WindowSize.Medium(width)
        else -> WindowSize.Large(width)
    }

    // Determine the window height class based on the height
    val windowHeightClass = when {
        height <= 480 -> WindowSize.Small(height)
        height in 481..639 -> WindowSize.SemiCompact(height)
        height in 640..720 -> WindowSize.Compact(height)
        height in 721..1080 -> WindowSize.Medium(height)
        else -> WindowSize.Large(height)
    }

    return WindowSizeClass(windowWidthClass, windowHeightClass)
}