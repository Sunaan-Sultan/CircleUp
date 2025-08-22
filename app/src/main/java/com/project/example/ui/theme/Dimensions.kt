package com.project.example.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
    val spacer1: Dp,
    val spacer2: Dp,
    val spacer3: Dp,
)

val smallDimensions = Dimensions(
    small = 2.dp,
    smallMedium = 4.dp,
    medium = 6.dp,
    mediumLarge = 9.dp,
    large = 23.dp,
    spacer1 = 50.dp,
    spacer2 = 50.dp,
    spacer3 = 50.dp,
)

val semiCompactDimensions = Dimensions(
    small = 3.dp,
    smallMedium = 5.dp,
    medium = 8.dp,
    mediumLarge = 30.dp,
    large = 15.dp,
    spacer1 = 50.dp,
    spacer2 = 50.dp,
    spacer3 = 50.dp,
)

val compactDimensions = Dimensions(
    small = 3.dp,
    smallMedium = 5.dp,
    medium = 8.dp,
    mediumLarge = 30.dp,
    large = 20.dp,
    spacer1 = 45.dp,
    spacer2 = 30.dp,
    spacer3 = 160.dp,
)

val mediumDimensions = Dimensions(
    small = 5.dp,
    smallMedium = 7.dp,
    medium = 10.dp,
    mediumLarge = 35.dp,
    large = 25.dp,
    spacer1 = 60.dp,
    spacer2 = 50.dp,
    spacer3 = 260.dp,
)

val largeDimensions = Dimensions(
    small = 8.dp,
    smallMedium = 11.dp,
    medium = 15.dp,
    mediumLarge = 20.dp,
    large = 35.dp,
    spacer1 = 50.dp,
    spacer2 = 50.dp,
    spacer3 = 50.dp,
)