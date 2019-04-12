package eli.solar.enums

internal enum class UnaryVerifier(private val message: String, private val predicate: Function1<Any?, Boolean>) {
    VALUE_TRUE("Expected condition for value[{2}] to be true.", { actual: Any? -> (actual as Boolean) }),
    VALUE_FALSE("Expected condition for value[{2}] to be false.", { actual: Any? -> !(actual as Boolean) }),
    VALUE_NULL("Expected value[{2}] to be null.",
        { actual: Any? -> actual == null }),
    VALUE_NULL_NOT("Expected value to be non-null.", { actual: Any? -> actual != null }),
    STRING_BLANK("Expected string[\"{2}\"] to be blank, empty, or null.",
        { actual: Any? -> actual == null || (actual as String).isBlank() }),
    STRING_BLANK_NOT("Expected string[\"{2}\"] to not be blank, empty, or null.",
        { actual: Any? -> actual != null && (actual as String).isNotBlank() }),
    STRING_EMPTY("Expected string[\"{2}\"] to be empty, or null.",
        { actual: Any? -> actual == null || (actual as String).isEmpty() }),
    STRING_EMPTY_NOT(
        "Expected string[\"{2}\"] to not be empty, or null.",
        { actual: Any? -> actual != null && (actual as String).isNotEmpty() }),
    COLLECTION_EMPTY(
        "Expected collection[{2}] to be empty, or null.",
        { actual: Any? -> actual == null || (actual as Collection<*>).isEmpty() }),
    COLLECTION_EMPTY_NOT(
        "Expected collection[{2}] to not be empty, or null.",
        { actual: Any? -> actual != null && (actual as Collection<*>).isNotEmpty() }),
    COLLECTION_CONTAINS_NULL_VALUES("Expected collection[{2}] to contain at least 1 null value.",
        { actual: Any? -> actual != null && (actual as Collection<*>).contains(null) }),
    COLLECTION_CONTAINS_NULL_VALUES_NOT("Expected collection[{2}] to not contain any null values.",
        { actual: Any? -> actual != null && !(actual as Collection<*>).contains(null) });

    fun getMessage(className: String, fieldName: String, value1: Any?): String {
        return CommonVerifier.ASSERTION_FAILED_FIELD_SINGLE.appendContext(
            message,
            className,
            fieldName,
            value1?.toString()
        )
    }

    fun check(value1: Any?): Boolean {
        return !predicate(value1)
    }
}