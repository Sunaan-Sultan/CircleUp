package com.project.example.ui.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.project.example.ui.theme.AppTheme
import com.project.example.ui.theme.BackgroundColor
import com.project.example.ui.theme.PrimaryColor
import com.project.example.ui.theme.White
import com.project.example.ui.theme.getCardColors
import com.project.repository.R

@Composable
fun RegistrationView(
    navController: NavHostController,
    viewModel: RegistrationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val (backgroundColor, contentColor) = getCardColors()
    val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) BackgroundColor else Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimens.large)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            EmailField(
                email = uiState.email,
                onEmailChange = viewModel::onEmailChanged,
                contentColor = contentColor,
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = uiState.password,
                onPasswordChange = viewModel::onPasswordChanged,
                isPasswordVisible = uiState.isPasswordVisible,
                onVisibilityToggle = viewModel::onPasswordVisibilityToggled,
                contentColor = contentColor,
                textColor = textColor,
                label = "Password",
                placeholder = "Enter Password",
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = uiState.confirmPassword,
                onPasswordChange = viewModel::onConfirmPasswordChanged,
                isPasswordVisible = uiState.isConfirmPasswordVisible,
                onVisibilityToggle = viewModel::onConfirmPasswordVisibilityToggled,
                contentColor = contentColor,
                textColor = textColor,
                label = "Confirm Password",
                placeholder = "Enter password again",
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            SignUpButton(
                onClick = viewModel::onSignUpClicked,
                isLoading = uiState.isLoading,
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (uiState.showDialog) {
            RegistrationDialog(
                validationResult = uiState.validationResult,
                isLoading = uiState.isLoading,
                onDismiss = viewModel::onDialogDismissed,
                onSuccess = {
                    viewModel.onDialogDismissed()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

@Composable
private fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    contentColor: Color,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("example@gmail.com", color = contentColor) },
        label = { Text("Email", color = contentColor) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        enabled = enabled,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = contentColor,
            unfocusedLabelColor = contentColor,
            focusedLabelColor = contentColor,
            unfocusedBorderColor = contentColor,
            focusedBorderColor = contentColor,
            cursorColor = contentColor,
            placeholderColor = contentColor
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityToggle: () -> Unit,
    contentColor: Color,
    textColor: Color,
    label: String,
    placeholder: String,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text(placeholder, color = contentColor) },
        label = { Text(label, color = contentColor) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        enabled = enabled,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = contentColor,
            unfocusedLabelColor = contentColor,
            focusedLabelColor = contentColor,
            unfocusedBorderColor = contentColor,
            focusedBorderColor = contentColor,
            cursorColor = contentColor,
            placeholderColor = contentColor
        ),
        trailingIcon = {
            Icon(
                painter = painterResource(if (isPasswordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_off_24),
                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                modifier = Modifier.clickable { onVisibilityToggle() },
                tint = textColor
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SignUpButton(
    onClick: () -> Unit,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryColor,
            contentColor = White
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = White,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Registering...", fontSize = 16.sp, color = Color.White)
        } else {
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
                color = Color.White
            )
        }
    }
}

@Composable
private fun RegistrationDialog(
    validationResult: ValidationResult,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    backgroundColor: Color,
    textColor: Color
) {
    if (isLoading) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryColor)
                }
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Creating your account...",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 16.sp,
                            color = textColor
                        )
                    )
                }
            },
            confirmButton = { },
            backgroundColor = backgroundColor
        )
        return
    }

    val isSuccess = validationResult is ValidationResult.Success
    val message = when (validationResult) {
        is ValidationResult.Success -> "Account has been successfully registered."
        is ValidationResult.Error -> validationResult.message
        ValidationResult.None -> ""
    }

    if (message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Image(
                    painter = painterResource(
                        id = if (isSuccess) R.drawable.success else R.drawable.high_importance_72
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = textColor
                        )
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (isSuccess) {
                                onSuccess()
                            } else {
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor,
                            contentColor = White
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Text("OK")
                    }
                }
            },
            backgroundColor = backgroundColor
        )
    }
}



