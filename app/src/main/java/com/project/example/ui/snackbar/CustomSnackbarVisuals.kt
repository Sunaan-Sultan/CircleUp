package com.project.example.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.Color

data class CustomSnackbarVisuals(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val containerColor: Color = Color.Red,
    val contentColor: Color = Color.White,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
) : SnackbarVisuals
