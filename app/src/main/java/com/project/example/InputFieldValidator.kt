package com.project.example

import androidx.compose.runtime.Composable

object InputFieldValidator {
    /**
     * Function to check if a given date string has a valid MM/DD/YYYY format.
     *
     * @param date Date string to be validated.
     * @return True if the date is valid, false otherwise.
     */
    @Composable
    fun isValidDateFormat(date: String): Boolean {
        val dateRegex = Regex("""\d{1,2}/\d{1,2}/\d{4}""")
        return dateRegex.matches(date)
    }

    /**
     * Function to validate various input fields based on the provided conditions.

     * @param isForgetPasswordView Flag indicating if it's a forget password view.
     * @param isRegistrationView Flag indicating if it's a registration view.
     * @param isBiometricRegistrationView Flag indicating if it's a biometric registration view.
     * @param isBiometricFingerprintRegistrationView Flag indicating if it's a biometric fingerprint registration view.
     *
     * @return Triple containing a boolean indicating if the validation is successful,
     *         a string message indicating the result, and a pair of booleans indicating
     *         if there are errors in the username and password fields.
     */
    @Composable
    fun validateFields(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        mobileNumber: String,
        nid: String,
        dob: String,
        password: String,
        confirmPassword: String,
        birthday: String?,
        isForgetPasswordView: Boolean,
        isRegistrationView: Boolean,
        isBiometricRegistrationView: Boolean,
        isBiometricFingerprintRegistrationView: Boolean,
    ): Triple<Boolean, String, Pair<Boolean, Boolean>> {
        var isUsernameError = false
        var isFirstNameError = false
        var isLastNameError = false
        var isMobileNumberError = false
        var isEmailError = false
        var isPasswordError = false
        var isConfirmPasswordError = false
        var isBirthdayError = false

        if (username.isEmpty()) {
            isUsernameError = true
        }

        if (firstName.isEmpty()) {
            isFirstNameError = true
        }

        if (lastName.isEmpty()) {
            isLastNameError = true
        }

        val phoneRegex = Regex("^(013|014|015|016|017|018|019)\\d{8}$")
        if (!phoneRegex.matches(mobileNumber)) {
            isMobileNumberError = true
        }

        val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
        if (!emailRegex.matches(email)) {
            isEmailError = true
        }

        val passwordRegex = Regex("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-_]).{8,}\$")
        if (!passwordRegex.matches(password)) {
            isPasswordError = true
        }

        if (password != confirmPassword) {
            isConfirmPasswordError = true
        }

        if (isForgetPasswordView) {
            if (birthday.isNullOrEmpty()) {
                isBirthdayError = true
            } else {
                // Validate date format and individual components
                val dateRegex = Regex("""\d{1,2}/\d{1,2}/\d{4}""")
                if (!dateRegex.matches(birthday)) {
                    isBirthdayError = true
                } else {
                    // Split the birthday into its components
                    val parts = birthday.split("/")
                    val month = parts[0].toIntOrNull()
                    val day = parts[1].toIntOrNull()
                    val year = parts[2].toIntOrNull()

                    // Validate the individual components of the date
                    isBirthdayError = month !in 1..12 || day !in 1..31 // improve this logic e.g. 02/31/1990 shouldbe invalid
                }
            }
        }

        // Check for the "Success" condition
        if (!isUsernameError && !isFirstNameError && !isLastNameError &&
            !isMobileNumberError && !isEmailError && !isPasswordError && !isConfirmPasswordError &&
            !isBirthdayError
        ) {
            return Triple(true, "Success", Pair(isUsernameError, isPasswordError))
        }

        // Handle error messages
        val errorMessage = when {
            isForgetPasswordView -> when {
                isUsernameError -> "Enter valid username"
                isBirthdayError -> {
                    if (birthday?.let { isValidDateFormat(it) } == true) {
                        "Enter date in MM/DD/YYYY format"
                    } else {
                        "Enter a valid date of birth"
                    }
                }
                isMobileNumberError -> "Enter valid phone number"
                isEmailError -> "Enter valid email"

                else -> "Your password is successfully changed. Please check your E-mail."
            }
            isRegistrationView -> when {
                isEmailError -> "Enter valid email"
                isPasswordError -> "Please create a valid password that is eight characters long, and includes one uppercase letter, one lowercase letter, one number, and one special character"
                confirmPassword.isEmpty() -> "Confirm Password field is empty, enter a valid password"
                isConfirmPasswordError -> "Password did not match"

                else -> "Account has been successfully registered"
            }
            isBiometricRegistrationView -> when {
                isUsernameError -> "Enter valid username"
                isPasswordError -> "Please enter a valid password that is eight characters long, and includes one uppercase letter, one lowercase letter, one number, and one special character"

                else -> "Unknown error"
            }
            isBiometricRegistrationView -> when {
                isUsernameError -> "Enter valid username"
                isPasswordError -> "Please enter a valid password that is eight characters long, and includes one uppercase letter, one lowercase letter, one number, and one special character"

                else -> "Unknown error"
            }

            else -> "Unknown error"
        }

        return Triple(false, errorMessage, Pair(isUsernameError, isPasswordError))
    }
}

