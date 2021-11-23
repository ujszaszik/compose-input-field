package hu.ujszaszik.compose.inputfield.extension

import java.util.regex.Pattern.compile

val String.Companion.empty: String
    get() = ""

val String.Companion.space: String
    get() = " "

val String?.isNotNull: Boolean
    get() = this != null

fun String?.removeWhitespaces() = this?.replace(String.space, String.empty)

fun String?.isNumeric(): Boolean = !isNullOrEmpty() && hasOnlyDigits()

private val digitRegex = compile("\\d+")

fun String.hasOnlyDigits(): Boolean {
    return digitRegex.matcher(this).matches()
}

fun String.withMaxLengthOf(maxLength: Int): String {
    return if (length >= maxLength) substring(0 until maxLength) else this
}