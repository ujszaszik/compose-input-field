package hu.ujszaszik.compose.inputfield.input.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import hu.ujszaszik.compose.inputfield.extension.*

object CreditCardTransformation : VisualTransformation {

    const val DIGITS_MAX_LENGTH = 16
    const val TEXT_MAX_LENGTH = 19

    fun isValidInput(text: String): Boolean {
        return text.isEmpty() || text.removeDashes().isNumeric()
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.withMaxLengthOf(DIGITS_MAX_LENGTH)
        var out = String.space
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i != 15) out += String.dash
        }

        val groupedDigitsOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return TEXT_MAX_LENGTH
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return DIGITS_MAX_LENGTH
            }
        }
        return TransformedText(AnnotatedString(out), groupedDigitsOffsetTranslator)
    }
}