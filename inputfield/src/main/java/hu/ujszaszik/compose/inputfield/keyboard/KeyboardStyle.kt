package hu.ujszaszik.compose.inputfield.keyboard

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

enum class KeyboardStyle(val keyboardOptions: KeyboardOptions) {
    Numeric(KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)),
    Password(KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)),
    Phone(KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone))
}