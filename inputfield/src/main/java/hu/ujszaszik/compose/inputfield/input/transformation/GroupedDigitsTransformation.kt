package hu.ujszaszik.compose.inputfield.input.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import hu.ujszaszik.compose.inputfield.extension.*

object GroupedDigitsTransformation : VisualTransformation {

    const val DIGITS_MAX_LENGTH = 12
    const val TEXT_MAX_LENGTH = 15

    fun isValidInput(text: String): Boolean {
        return text.isEmpty() || text.removeWhitespaces().isNumeric()
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.withMaxLengthOf(DIGITS_MAX_LENGTH)
        var out = String.empty
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 3 == 2 && i != 12) out += String.space
        }

        val groupedDigitsOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset + 1
                if (offset <= 8) return offset + 2
                if (offset <= 11) return offset + 3
                return TEXT_MAX_LENGTH
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset - 1
                if (offset <= 11) return offset - 2
                if (offset <= 15) return offset - 3
                return DIGITS_MAX_LENGTH
            }
        }

        return TransformedText(AnnotatedString(out), groupedDigitsOffsetTranslator)
    }

}