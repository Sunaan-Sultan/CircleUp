package com.project.example.ui.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.example.isWithinMaxCharLimit
import com.project.repository.R
import com.project.example.ui.theme.PrimaryColor
import com.project.example.ui.theme.White
import com.project.example.ui.theme.getCardColors
import com.project.example.InputFieldValidator.validateFields
import com.project.example.ui.theme.AppTheme
import com.project.example.ui.theme.BackgroundColor
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun RegistrationView(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var nid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isValidationErrorDialogVisible by remember { mutableStateOf(false) }
    var isBirthdayError by remember {
        mutableStateOf(false)
    }
    val dateDialogState = rememberMaterialDialogState()
    val context = LocalContext.current

    val (isValidationSuccess, errorMessage) = validateFields(
        username,
        firstName,
        lastName,
        email,
        mobileNumber,
        nid,
        dob,
        password,
        confirmPassword,
        null,

        isForgetPasswordView = false,
        isRegistrationView = true,
        isBiometricRegistrationView = false,
        isBiometricFingerprintRegistrationView = false,
    )

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val (backgroundColor, contentColor) = getCardColors()

    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black

    Column(
        modifier = Modifier
            .padding(
                //top = AppTheme.dimens.,
                start = AppTheme.dimens.large,
                end = AppTheme.dimens.large,
            )
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) BackgroundColor else Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 40)) {
                    username = it
                }
            },
            placeholder = { Text("Enter your username", color = contentColor) },
            label = { Text("Username", color = contentColor) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii,
            ),
            singleLine = true,
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
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 40)) {
                    firstName = it
                }
            },
            placeholder = { Text("Enter your First Name", color = contentColor) },
            label = { Text("First Name", color = contentColor) },

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii,
            ),
            singleLine = true,
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
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 40)) {
                    lastName = it
                }
            },
            placeholder = { Text("Enter your Last Name", color = contentColor) },
            label = { Text("Last Name", color = contentColor) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii,
            ),
            singleLine = true,
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
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 40)) {
                    email = it
                }
            },
            placeholder = { Text("example@gmail.com", color = contentColor) },
            label = { Text("Email", color = contentColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
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
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 11)) {
                    mobileNumber = it
                }
            },
            placeholder = { Text("880XXXXXXXXX", color = contentColor) },
            label = { Text("Mobile Number", color = contentColor) },
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
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = nid,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 20)) {
                    nid = it
                }
            },
            placeholder = { Text("National Id", color = contentColor) },
            label = { Text("GID", color = contentColor) },
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
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = dob,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 10)) {
                    dob = it
                    isBirthdayError = it.isEmpty()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Date of Birth", color = contentColor) },
            placeholder = { Text("MM/DD/YYYY", color = contentColor) },
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
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    contentDescription = "Calendar Icon",
                    modifier = Modifier
                        .clickable {
                            dateDialogState.show()
                        }
                        .padding(8.dp),
                    tint = textColor,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Ascii,
            ),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 20)) {
                    password = it
                }
            },
            placeholder = { Text("Enter Password", color = contentColor) },
            label = { Text("Password", color = contentColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
            trailingIcon = {
                val visibilityIcon =
                    if (passwordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                val visibilityIconContentDescription =
                    if (passwordVisible) "Hide password" else "Show password"
                Icon(
                    painter = painterResource(id = visibilityIcon),
                    contentDescription = visibilityIconContentDescription,
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                    tint = textColor,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                if (isWithinMaxCharLimit(it, 20)) {
                    confirmPassword = it
                }
            },
            placeholder = { Text("Enter password again", color = contentColor) },
            label = { Text("Confirm Password", color = contentColor) },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
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
            trailingIcon = {
                val visibilityIcon =
                    if (confirmPasswordVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                val visibilityIconContentDescription =
                    if (confirmPasswordVisible) "Hide password" else "Show password"
                Icon(
                    painter = painterResource(id = visibilityIcon),
                    contentDescription = visibilityIconContentDescription,
                    modifier = Modifier.clickable {
                        confirmPasswordVisible = !confirmPasswordVisible
                    },
                    tint = textColor,
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                if (isValidationSuccess) {
                    val registrationBean = RegistrationPresenter()
                    val response = registrationBean.registration(
                        context,
                        username,
                        firstName,
                        lastName,
                        email,
                        mobileNumber,
                        nid,
                        dob,
                        password,
                        confirmPassword,
                    )
                    if (response) {
                        isValidationErrorDialogVisible = true
                    }
                } else {
                    isValidationErrorDialogVisible = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = PrimaryColor,
                contentColor = White,
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = "Sign Up",
                fontSize = 16.sp,
                modifier = Modifier.padding(5.dp),
                color = Color.White,
            )
        }
    }

    if (isValidationErrorDialogVisible) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Image(
                    painter = if (errorMessage == "Success") {
                        painterResource(id = R.drawable.success)
                    } else {
                        painterResource(id = R.drawable.high_importance_72)
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        if (errorMessage == "Success") {
                            "Account has been successfully registered."
                        } else {
                            errorMessage
                        },
                        color = contentColor,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                            .copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = textColor,
                            ),
                    )
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(
                        onClick = {
                            if (errorMessage == "Success") {
                                isValidationErrorDialogVisible = false
                                navController.navigate("login")
                            } else {
                                isValidationErrorDialogVisible = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor,
                            contentColor = White,
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 12.dp),
                    ) {
                        Text("Ok")
                    }
                }
            },
            backgroundColor = backgroundColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationView() {
    val fakeNavController = rememberNavController()

    RegistrationView(navController = fakeNavController)
}



