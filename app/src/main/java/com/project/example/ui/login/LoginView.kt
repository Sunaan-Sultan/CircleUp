package com.project.example.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.project.example.PreferencesManager
import com.project.example.isWithinMaxCharLimit
import com.project.example.ui.snackbar.CustomSnackbarVisuals
import com.project.example.ui.theme.AppTheme
import com.project.example.ui.theme.BackgroundColor
import com.project.example.ui.theme.CircleUpTheme
import com.project.example.ui.theme.Orientation
import com.project.example.ui.theme.PrimaryColor2
import com.project.example.ui.theme.rememberWindowSizeClass
import com.project.repository.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginView(navController: NavHostController, preferencesManager: PreferencesManager) {
    var email by remember { mutableStateOf(preferencesManager.getKey("email", "")) }
    var password by remember { mutableStateOf(preferencesManager.getKey("password", "")) }
    var checked by remember { mutableStateOf(email.isNotEmpty() && password.isNotEmpty()) }
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }

    val window = rememberWindowSizeClass()
    CircleUpTheme(window) {
        if (AppTheme.orientation == Orientation.Portrait) {
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

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            if (isWithinMaxCharLimit(it, 40)) {
                                email = it
                            }
                        },
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
                                contentDescription = "email Icon",
                            )
                        },
                        trailingIcon = {
                            if (email.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear Icon",
                                    modifier = Modifier.clickable { email = "" },
                                    tint = contentColor,
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Ascii,
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            if (isWithinMaxCharLimit(it, 20)) {
                                password = it
                            }
                        },
                        label = { Text("Password", color = contentColor, fontSize = 16.sp) },
                        placeholder = { Text("Enter your password", color = contentColor, fontSize = 16.sp) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                            val visibilityIcon =
                                if (passwordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                            val visibilityIconContentDescription =
                                if (passwordVisible) "Hide password" else "Show password"
                            Icon(
                                painter = painterResource(id = visibilityIcon),
                                contentDescription = visibilityIconContentDescription,
                                modifier = Modifier.clickable {
                                    passwordVisible = !passwordVisible
                                },
                                tint = contentColor,
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Ascii,
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { isChecked ->
                                checked = isChecked
                                if (!isChecked) {
                                    preferencesManager.removeKey("email")
                                    preferencesManager.removeKey("password")
                                    email = ""
                                    password = ""
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = contentColor,
                                checkedColor = contentColor,
                            ),
                            modifier = Modifier.offset(x = (-10).dp),
                        )
                        Text(
                            text = "Remember me",
                            color = contentColor,
                            modifier = Modifier.offset(x = (-16).dp),
                        )

                        DisposableEffect(Unit) {
                            onDispose {
                                if (checked) {
                                    preferencesManager.saveKey("email", email)
                                    preferencesManager.saveKey("password", password)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            val loginBean = LoginPresenter(context)
                            val authResult = loginBean.login(email, password)

                            when (authResult) {
                                is AuthResult.Success -> {
                                    val memberId = authResult.user.id.toString()
                                    preferencesManager.saveKey("userId", memberId)
                                    Log.d("LOGIN", "Saved userId = $memberId")
                                    preferencesManager.saveKey("userRole", authResult.user.userRole.lowercase())
                                    Log.d("LOGIN", "Saved userRole = ${authResult.user.userRole.lowercase()}")
                                    preferencesManager.saveUsername("user", email)
                                    preferencesManager.savePassword("pass", password)

                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                                AuthResult.InvalidUsername -> {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Wrong username")
                                    }
                                }
                                AuthResult.InvalidPassword -> {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Wrong password")
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = AppTheme.dimens.large),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor2,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(3.dp)
                        )
                    }

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
                            style = MaterialTheme.typography.body1
                                .copy(
                                    textDecoration = TextDecoration.Underline,
                                    color = PrimaryColor2,
                                ),

                            onClick = {
                                navController.navigate("registration")
                            },
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                    }
                }
            }
        }
    }
}