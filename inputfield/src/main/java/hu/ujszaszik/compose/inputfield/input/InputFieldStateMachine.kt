package hu.ujszaszik.compose.inputfield.input

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.tinder.StateMachine
import hu.ujszaszik.compose.inputfield.resources.Colors

sealed class InputFieldState(val style: InputFieldStyle) {

    abstract val strokeColor: Color
    abstract val cursorColor: Color
    abstract val labelHeight: Dp
    abstract val labelBgColor: Color
    abstract val labelTextSize: Float
    abstract val labelTextColor: Color

    class Initial(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = style.unfocusedStrokeColor
        override val cursorColor = Colors.fontPrimary
        override val labelHeight = style.labelInitialHeight
        override val labelBgColor = Colors.transparent
        override val labelTextSize = style.labelInitialSize
        override val labelTextColor = Colors.fontPrimary
    }

    class InitialError(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = Colors.red
        override val cursorColor = Colors.red
        override val labelHeight = style.labelInitialHeight
        override val labelBgColor = Colors.transparent
        override val labelTextSize = style.labelInitialSize
        override val labelTextColor = Colors.red
    }

    class Unfocused(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = style.unfocusedStrokeColor
        override val cursorColor = Colors.fontPrimary
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.fontPrimary
    }

    class UnfocusedError(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = Colors.red
        override val cursorColor = Colors.red
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.red
    }

    class Focused(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = style.focusedStrokeColor
        override val cursorColor = Colors.green
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.green
    }

    class FocusedError(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = Colors.red
        override val cursorColor = Colors.red
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.red
    }

    class DisabledAndEmpty(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = style.unfocusedStrokeColor
        override val cursorColor = Colors.gray
        override val labelHeight = style.labelInitialHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelInitialSize
        override val labelTextColor = Colors.fontPrimary
    }

    class DisabledAndEmptyError(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = Colors.red
        override val cursorColor = Colors.gray
        override val labelHeight = style.labelInitialHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelInitialSize
        override val labelTextColor = Colors.red
    }

    class DisabledWithValue(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = style.unfocusedStrokeColor
        override val cursorColor = Colors.gray
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.fontPrimary
    }

    class DisabledWithValueError(style: InputFieldStyle) : InputFieldState(style) {
        override val strokeColor = Colors.red
        override val cursorColor = Colors.gray
        override val labelHeight = style.labelFocusedHeight
        override val labelBgColor = Colors.white
        override val labelTextSize = style.labelFocusedSize
        override val labelTextColor = Colors.red
    }

}

sealed class InputFieldEvent {
    object GrabFocus : InputFieldEvent()
    object ClearFocus : InputFieldEvent()
    object ShowError : InputFieldEvent()
    object RemoveError : InputFieldEvent()
    object Disable : InputFieldEvent()
}


fun stateMachine(style: InputFieldStyle) =
    StateMachine.create<InputFieldState, InputFieldEvent, Nothing> {
        initialState(InputFieldState.Initial(style))

        state<InputFieldState.Initial> {
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.Focused(style))
            }
            on<InputFieldEvent.ShowError> {
                transitionTo(InputFieldState.InitialError(style))
            }
            on<InputFieldEvent.Disable> {
                transitionTo(InputFieldState.DisabledAndEmpty(style))
            }
        }

        state<InputFieldState.InitialError> {
            on<InputFieldEvent.RemoveError> {
                transitionTo(InputFieldState.Initial(style))
            }
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.FocusedError(style))
            }
        }

        state<InputFieldState.Focused> {
            on<InputFieldEvent.ShowError> {
                transitionTo(InputFieldState.FocusedError(style))
            }
            on<InputFieldEvent.ClearFocus> {
                transitionTo(InputFieldState.Unfocused(style))
            }
        }

        state<InputFieldState.FocusedError> {
            on<InputFieldEvent.RemoveError> {
                transitionTo(InputFieldState.Focused(style))
            }
            on<InputFieldEvent.ClearFocus> {
                transitionTo(InputFieldState.UnfocusedError(style))
            }
        }

        state<InputFieldState.Unfocused> {
            on<InputFieldEvent.ShowError> {
                transitionTo(InputFieldState.UnfocusedError(style))
            }
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.Focused(style))
            }
        }

        state<InputFieldState.UnfocusedError> {
            on<InputFieldEvent.RemoveError> {
                transitionTo(InputFieldState.Unfocused(style))
            }
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.FocusedError(style))
            }
        }

        state<InputFieldState.DisabledAndEmpty> {
            on<InputFieldEvent.ShowError> {
                transitionTo(InputFieldState.DisabledAndEmptyError(style))
            }
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.DisabledWithValue(style))
            }
        }

        state<InputFieldState.DisabledAndEmptyError> {
            on<InputFieldEvent.RemoveError> {
                transitionTo(InputFieldState.DisabledAndEmpty(style))
            }
            on<InputFieldEvent.GrabFocus> {
                transitionTo(InputFieldState.DisabledWithValueError(style))
            }
        }

        state<InputFieldState.DisabledWithValue> {
            on<InputFieldEvent.ShowError> {
                transitionTo(InputFieldState.DisabledWithValueError(style))
            }
            on<InputFieldEvent.ClearFocus> {
                transitionTo(InputFieldState.DisabledAndEmpty(style))
            }
        }

        state<InputFieldState.DisabledWithValueError> {
            on<InputFieldEvent.RemoveError> {
                transitionTo(InputFieldState.DisabledWithValue(style))
            }
            on<InputFieldEvent.ClearFocus> {
                transitionTo(InputFieldState.DisabledAndEmptyError(style))
            }
        }

    }