package hu.ujszaszik.compose.inputfield.focus

enum class FocusEvent {
    REQUEST, CLEAR;

    fun isRequested(): Boolean = this == REQUEST

    fun isCleared(): Boolean = this == CLEAR
}