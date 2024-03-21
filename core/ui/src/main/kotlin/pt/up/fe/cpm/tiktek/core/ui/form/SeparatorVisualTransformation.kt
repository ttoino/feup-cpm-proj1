package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A [VisualTransformation] that adds [separator] every [chunk] characters, until the text size reaches [maxSize].
 *
 * Example:
 *
 * `SeparatorVisualTransformation(4, '-', 16)` will transform "1234567890" into "1234-5678-90".
 * `SeparatorVisualTransformation(3, '.', 12)` will transform "123456789" into "123.456.789.",
 *   and "123456789012" into "123.456.789.012".
 *
 * @param chunk the number of characters between separators
 * @param separator the character to use as separator
 */
class SeparatorVisualTransformation(
    private val chunk: Int,
    private val separator: Char = ' ',
    private val maxSize: Int = Int.MAX_VALUE,
): VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText
        = TransformedText(
            AnnotatedString(
                text.text.chunked(chunk).joinToString(separator.toString()) +
                if (text.text.isNotEmpty() && text.text.length < maxSize && text.text.length % chunk == 0) separator else ""
            ),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int
                    = offset + (offset - 1) / chunk

                override fun transformedToOriginal(offset: Int): Int
                    = offset - (offset - 1) / chunk
            }
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SeparatorVisualTransformation

        if (chunk != other.chunk) return false
        if (separator != other.separator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chunk
        result = 31 * result + separator.hashCode()
        return result
    }
}