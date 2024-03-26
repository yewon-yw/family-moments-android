package io.familymoments.app.core.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class FMVisualTransformation(private val maskChar: Char = '●') : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text
        val output = buildAnnotatedString {
            input.forEach { _ ->
                append(maskChar)
            }
        }
        return TransformedText(output, OffsetMapping.Identity)
    }
}
