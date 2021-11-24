package hu.ujszaszik.compose.inputfield

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.ujszaszik.compose.inputfield.extension.empty
import hu.ujszaszik.compose.inputfield.extension.isNumeric
import hu.ujszaszik.compose.inputfield.input.InputField
import hu.ujszaszik.compose.inputfield.input.transformation.CreditCardTransformation
import hu.ujszaszik.compose.inputfield.input.transformation.GroupedDigitsTransformation
import hu.ujszaszik.compose.inputfield.keyboard.KeyboardStyle
import hu.ujszaszik.compose.inputfield.layout.CenteredColumn

@Composable
@Preview
fun TestNumericInputField() {
    var enteredText by remember { mutableStateOf(String.empty) }

    val isError = enteredText.isNotEmpty() && enteredText.toInt() % 2 != 0
    val errorTextValue = if (isError) "Not an even number" else null

    CenteredColumn {
        InputField(
            labelText = "Amount",
            keyboardStyle = KeyboardStyle.Numeric,
            onTextChange = { enteredText = it },
            maxLength = 9,
            validator = { it.isEmpty() || it.isNumeric() },
            visualTransformation = GroupedDigitsTransformation,
            isError = isError,
            errorTextValue = errorTextValue
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "The text you entered is: $enteredText")
    }
}

@Composable
@Preview
fun TestCreditCardInputField() {
    var enteredText by remember { mutableStateOf(String.empty) }

    CenteredColumn {
        InputField(
            labelText = "Credit Card Number",
            keyboardStyle = KeyboardStyle.Numeric,
            onTextChange = { enteredText = it },
            maxLength = CreditCardTransformation.DIGITS_MAX_LENGTH,
            validator = { it.isEmpty() || it.isNumeric() },
            visualTransformation = CreditCardTransformation,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Your credit Card Number is: $enteredText")
    }
}