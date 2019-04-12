package eli.solar.enums

import java.text.MessageFormat

internal enum class CommonVerifier(private val message: String) {
    ASSERTION_FAILED_FIELD_SINGLE("Assertion failed for field: \"{0}#{1}\""),
    ASSERTION_FAILED_FIELDS_DOUBLE("Assertion failed for fields: \"{0}#{1}\" and \"{0}#{2}\"");

    private val spacer: String = ";\t"

    fun appendContext(context: String, vararg values: String?): String {
        return MessageFormat.format(message + spacer + context, *values)
    }
}