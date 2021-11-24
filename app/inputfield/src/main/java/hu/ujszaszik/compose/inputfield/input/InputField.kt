package hu.ujszaszik.compose.inputfield.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import hu.ujszaszik.compose.inputfield.extension.empty
import hu.ujszaszik.compose.inputfield.extension.isNotNull
import hu.ujszaszik.compose.inputfield.focus.FocusEvent
import hu.ujszaszik.compose.inputfield.keyboard.KeyboardStyle
import hu.ujszaszik.compose.inputfield.layout.CenteredColumn
import hu.ujszaszik.compose.inputfield.resources.Dimens

@Composable
fun InputField(
    labelText: String,
    textValue: String = String.empty,
    onTextChange: (String) -> Unit = {},
    style: InputFieldStyle = InputFieldStyle(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int? = null,
    keyboardStyle: KeyboardStyle,
    validator: (String) -> Boolean = { true },
    isDisabled: Boolean = false,
    isError: Boolean = false,
    errorTextValue: String? = null
) {
    var text by remember { mutableStateOf(String.empty) }
    var validatedText by remember { mutableStateOf(String.empty) }

    if (validator.invoke(text)) {
        if ((maxLength != null && text.length <= maxLength) || maxLength == null) {
            validatedText = text.also { onTextChange.invoke(validatedText) }
        }
    }

    val stateMachine by remember { mutableStateOf(stateMachine(style)) }
    if (isDisabled) stateMachine.transition(InputFieldEvent.Disable)

    var focusEvent by remember { mutableStateOf(FocusEvent.CLEAR) }

    if (focusEvent.isRequested()) stateMachine.transition(InputFieldEvent.GrabFocus)
    else stateMachine.transition(InputFieldEvent.ClearFocus)

    val hasErrorState = isError || errorTextValue.isNotNull
    if (hasErrorState) stateMachine.transition(InputFieldEvent.ShowError)
    else stateMachine.transition(InputFieldEvent.RemoveError)

    if (textValue.isNotEmpty()) {
        stateMachine.transition(InputFieldEvent.GrabFocus)
        text = textValue
    } else if (textValue.isEmpty() && isDisabled) {
        stateMachine.transition(InputFieldEvent.ClearFocus)
        text = textValue
    }

    val focusRequester = remember { FocusRequester() }

    val sizeAnimation: Dp by animateDpAsState(stateMachine.state.labelHeight)

    val textSizeAnimation: Float by animateFloatAsState(stateMachine.state.labelTextSize)

    val colorAnimation: Color by animateColorAsState(stateMachine.state.labelBgColor)

    val textColorAnimation: Color by animateColorAsState(stateMachine.state.labelTextColor)

    CenteredColumn(modifier = Modifier.height(style.height)) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(style.layoutHeight)
        ) {
            Surface(
                elevation = style.elevation,
                shape = RoundedCornerShape(style.cornerRadius),
                modifier = Modifier
                    .border(
                        BorderStroke(style.strokeWidth, stateMachine.state.strokeColor),
                        shape = RoundedCornerShape(style.cornerRadius)
                    )
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(style.boxHeight)
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .height(style.textAreaHeight)
                            .padding(
                                vertical = style.verticalPadding,
                                horizontal = style.horizontalPadding
                            )
                            .focusTarget()
                            .focusRequester(focusRequester)
                            .onFocusChanged { state ->
                                focusEvent =
                                    if (state.isFocused) FocusEvent.REQUEST
                                    else FocusEvent.CLEAR
                            }
                            .pointerInput(Unit) { detectTapGestures { focusRequester.requestFocus() } },
                        enabled = !isDisabled,
                        value = validatedText,
                        onValueChange = { text = it },
                        textStyle = TextStyle(
                            color = style.inputTextColor,
                            fontFamily = style.inputTextFontId,
                            fontSize = style.inputTextSize
                        ),
                        cursorBrush = SolidColor(stateMachine.state.cursorColor),
                        keyboardOptions = keyboardStyle.keyboardOptions,
                        visualTransformation = visualTransformation
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = Dimens.paddingDefault)
                    .height(sizeAnimation),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = labelText,
                    color = textColorAnimation,
                    fontFamily = style.labelTextFontId,
                    maxLines = 1,
                    fontSize = textSizeAnimation.sp,
                    modifier = Modifier
                        .background(colorAnimation)
                        .height(IntrinsicSize.Max)
                        .width(IntrinsicSize.Max)
                        .clickable {
                            if (!isDisabled) {
                                focusEvent = FocusEvent.REQUEST
                                focusRequester.requestFocus()
                            }
                        }
                )
            }
        }
        errorTextValue?.let {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(start = Dimens.paddingDefault, top = Dimens.paddingQuarter)
                    .fillMaxWidth()
            ) {
                Text(
                    text = errorTextValue,
                    fontSize = style.errorTextSize,
                    color = style.errorTextColor,
                    fontFamily = style.errorTextFontId,
                    maxLines = 1
                )
            }
        }
    }
}