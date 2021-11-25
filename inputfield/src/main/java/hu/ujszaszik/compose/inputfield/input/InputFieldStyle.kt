package hu.ujszaszik.compose.inputfield.input

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ujszaszik.compose.inputfield.resources.Colors

open class InputFieldStyle {
    open val height = 80.dp
    open val boxHeight = 50.dp
    open val layoutHeight = 57.dp
    open val textAreaHeight = 40.dp
    open val cornerRadius = 24.dp
    open val elevation = 6.dp
    open val verticalPadding = 10.dp
    open val horizontalPadding = 16.dp

    open val inputTextSize = 14.sp
    open val inputTextColor = Colors.fontPrimary
    open val inputTextFontId = FontFamily.SansSerif

    open val strokeWidth = 1.dp
    open val unfocusedStrokeColor = Colors.gray
    open val focusedStrokeColor = Colors.green

    open val labelInitialHeight = 42.dp
    open val labelFocusedHeight = 14.dp
    open val labelInitialSize = 14F
    open val labelFocusedSize = 10F
    open val labelTextFontId = FontFamily.SansSerif

    open val errorTextSize = 13.sp
    open val errorTextColor = Colors.red
    open val errorTextFontId = FontFamily.SansSerif
}