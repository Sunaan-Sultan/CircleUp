package com.project.example

import java.text.DecimalFormat

fun formatNumberWithCommas(number: Double, decimalPlaces: Int = 2): String {
    // Define the format pattern based on the specified number of decimal places
    val formatPattern = if (decimalPlaces > 0) {
        "#,##,##0.${"0".repeat(decimalPlaces)}"
    } else {
        "#,##,##0"
    }
    val decimalFormat = DecimalFormat(formatPattern)
    // Format the number using the DecimalFormat
    return decimalFormat.format(number)
}

fun isWithinMaxCharLimit(input: String, maxChar: Int): Boolean {
    return input.length <= maxChar
}