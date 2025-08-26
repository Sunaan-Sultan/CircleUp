package com.project.example.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.project.example.PreferencesManager
import com.project.example.ui.snackbar.CustomSnackbarVisuals
import com.project.example.ui.theme.*
import com.project.repository.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    preferencesManager: PreferencesManager
) {
    val window = rememberWindowSizeClass()
    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel {
        LoginViewModel(context, preferencesManager)
    }

    CircleUpTheme(window) {
        if (AppTheme.orientation == Orientation.Portrait) {
            LoginView(navController, viewModel)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun LoginView(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle login success
    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
                launchSingleTop = true
            }
            viewModel.resetLoginState()
        }
    }

    // Handle error messages
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData: SnackbarData ->
                    val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals
                    if (customVisuals != null) {
                        Snackbar(
                            snackbarData = snackbarData,
                            contentColor = customVisuals.contentColor,
                            containerColor = customVisuals.containerColor,
                        )
                    } else {
                        Snackbar(snackbarData = snackbarData)
                    }
                },
            )
        },
        backgroundColor = if (isSystemInDarkTheme()) BackgroundColor else Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = AppTheme.dimens.mediumLarge,
                    start = AppTheme.dimens.large,
                    end = AppTheme.dimens.large,
                )
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) BackgroundColor else Color.White),
        ) {
            // App Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "CircleUp",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(y = (20).dp),
                    color = PrimaryColor2,
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.dimens.spacer1))

            // Email Field
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChanged,
                label = { Text("E-mail", color = contentColor, fontSize = 16.sp) },
                placeholder = { Text("Enter your email", color = contentColor, fontSize = 14.sp) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = contentColor,
                    unfocusedLabelColor = contentColor,
                    focusedLabelColor = contentColor,
                    unfocusedBorderColor = contentColor,
                    focusedBorderColor = contentColor,
                    cursorColor = contentColor,
                    leadingIconColor = contentColor,
                    placeholderColor = contentColor,
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Email Icon",
                    )
                },
                trailingIcon = {
                    if (uiState.email.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Icon",
                            modifier = Modifier.clickable { viewModel.onEmailChanged("") },
                            tint = contentColor,
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                ),
                singleLine = true,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Password Field
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                label = { Text("Password", color = contentColor, fontSize = 16.sp) },
                placeholder = { Text("Enter your password", color = contentColor, fontSize = 16.sp) },
                visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = contentColor,
                    unfocusedLabelColor = contentColor,
                    unfocusedBorderColor = contentColor,
                    focusedBorderColor = contentColor,
                    focusedLabelColor = contentColor,
                    cursorColor = contentColor,
                    leadingIconColor = contentColor,
                    placeholderColor = contentColor,
                ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password Icon",
                    )
                },
                trailingIcon = {
                    val visibilityIcon = if (uiState.isPasswordVisible)
                        R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                    val visibilityIconContentDescription = if (uiState.isPasswordVisible)
                        "Hide password" else "Show password"
                    Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = visibilityIconContentDescription,
                        modifier = Modifier.clickable { viewModel.onPasswordVisibilityToggled() },
                        tint = contentColor,
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                ),
                singleLine = true,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )

            // Remember Me Checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.isRememberMeChecked,
                    onCheckedChange = { viewModel.onRememberMeToggled() },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = contentColor,
                        checkedColor = contentColor,
                    ),
                    enabled = !uiState.isLoading,
                    modifier = Modifier.offset(x = (-10).dp),
                )
                Text(
                    text = "Remember me",
                    color = contentColor,
                    modifier = Modifier.offset(x = (-16).dp),
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // Login Button
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onLoginClicked()
                },
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.large),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor2,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Signing in...",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(3.dp)
                    )
                } else {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }

            // Sign Up Link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 16.dp, start = 16.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.body1,
                    color = contentColor,
                )
                ClickableText(
                    text = AnnotatedString("Sign Up"),
                    style = MaterialTheme.typography.body1.copy(
                        textDecoration = TextDecoration.Underline,
                        color = PrimaryColor2,
                    ),
                    onClick = if (!uiState.isLoading) {
                        { navController.navigate("registration")}
                    } else {
                        { }
                    },
                )
            }
        }
    }
}