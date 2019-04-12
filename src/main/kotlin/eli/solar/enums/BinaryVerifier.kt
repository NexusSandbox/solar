package eli.solar.enums

internal enum class BinaryVerifier(private val message: String, private val predicate: Function2<Any?, Any?, Boolean>) {
    VALUE_EITHER(
        "Expected exactly 1 condition for value1[{3}] or value2[{4}] to be true, but not both or neither.",
        { actual1: Any?, actual2: Any? -> (actual1 as Boolean).xor(actual2 as Boolean) }),
    VALUE_EITHER_NOT(
        "Expected either both or neither conditions for value1[{3}] and value2[{4}] to be true.",
        { actual1: Any?, actual2: Any? -> !(actual1 as Boolean).xor(actual2 as Boolean) }),
    VALUE_EITHER_NULL("Expected exactly 1 value1[{3}] or value2[{4}] to be non-null, but not both or neither.",
        { actual1: Any?, actual2: Any? -> (actual1 != null).xor(actual2 != null) }),
    VALUE_EITHER_NULL_NOT("Expected either both or neither value1[{3}] and value2[{4}] to be non-null.",
        { actual1: Any?, actual2: Any? -> !(actual1 != null).xor(actual2 != null) }),
    COLLECTION_CONTAINS_ALL_VALUES(
        "Expected collection[{2}] to contain all values from collection[{3}].",
        { actual: Any?, expected: Any? ->
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> (expected as Collection<*>).isNullOrEmpty()
                (expected as Collection<*>).isNullOrEmpty() -> true
                else -> actual.containsAll(expected)
            }
        }),
    COLLECTION_CONTAINS_ANY_VALUES(
        "Expected collection[{2}] to contain any values from collection[{3}].",
        { actual: Any?, expected: Any? ->
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> (expected as Collection<*>).isNullOrEmpty()
                (expected as Collection<*>).isNullOrEmpty() -> true
                else -> actual.any { expected.contains(it) }
            }
        }),
    COLLECTION_CONTAINS_NO_VALUES("Expected collection[{2}] to contain no values from collection[{3}].",
        { actual: Any?, expected: Any? ->
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> (expected as Collection<*>).isNullOrEmpty()
                (expected as Collection<*>).isNullOrEmpty() -> true
                else -> actual.none { expected.contains(it) }
            }
        }),
    COLLECTION_EQUAL("Expected collection[{2}] ≡ collection[{3}].",
        { actual: Any?, expected: Any? -> (actual as Collection<*>) == (expected as Collection<*>) }),
    COLLECTION_EQUAL_NOT("Expected collection[{2}] ≠ collection[{3}].",
        { actual: Any?, expected: Any? -> (actual as Collection<*>) != (expected as Collection<*>) }),
    STRING_EQUAL("Expected string[\"{2}\"] ≡ string[\"{3}\"].",
        { actual: Any?, expected: Any? -> (actual as String) == (expected as String) }),
    STRING_EQUAL_NOT("Expected string[\"{2}\"] ≠ string[\"{3}\"].",
        { actual: Any?, expected: Any? -> (actual as String) != (expected as String) }),
    VALUE_EQUAL("Expected value[{2}] ≡ value[{3}].",
        { actual: Any?, expected: Any? -> actual == expected }),
    VALUE_EQUAL_NOT("Expected value[{2}] ≠ value[{3}].",
        { actual: Any?, expected: Any? -> actual != expected }),
    STRING_MATCHES("Expected string[\"{2}\"] to match pattern[\"{3}\"].",
        { actual: Any?, matcher: Any? ->
            when (actual as String) {
                actual -> (matcher as Regex).matches(actual)
                else -> false
            }
        }),
    STRING_MATCHES_NOT("Expected string[\"{2}\"] to not match pattern[\"{3}\"].",
        { actual: Any?, matcher: Any? ->
            when (actual as String) {
                actual -> !(matcher as Regex).matches(actual)
                else -> false
            }
        }),
    COLLECTION_MATCHES_ALL_VALUES("Expected collection[{2}] to match all values with predicate.",
        { actual: Any?, matcher: Any? ->
            @Suppress("UNCHECKED_CAST")
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> true
                (matcher as Function1<Any?, Boolean>) -> actual.all { matcher.invoke(it) }
                else -> false
            }
        }),
    COLLECTION_MATCHES_ANY_VALUES("Expected collection[{2}] to match any values with predicate.",
        { actual: Any?, matcher: Any? ->
            @Suppress("UNCHECKED_CAST")
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> true
                (matcher as Function1<Any?, Boolean>) -> actual.any { matcher.invoke(it) }
                else -> false
            }
        }),
    COLLECTION_MATCHES_NO_VALUES("Expected collection[{2}] to match no values with predicate.",
        { actual: Any?, matcher: Any? ->
            @Suppress("UNCHECKED_CAST")
            when (actual) {
                (actual as Collection<*>).isNullOrEmpty() -> true
                (matcher as Function1<Any?, Boolean>) -> actual.none { matcher.invoke(it) }
                else -> false
            }
        }),
    STRING_LENGTH_WITHIN("Expected string[\"{2}\"] to have length < value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length < (length as Int)
                else -> false
            }
        }),
    STRING_LENGTH_WITHIN_NOT("Expected string[\"{2}\"] to have length ≥ value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length >= (length as Int)
                else -> false
            }
        }),
    STRING_LENGTH_EXCEEDS("Expected string[\"{2}\"] to have length > value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length > (length as Int)
                else -> false
            }
        }),
    STRING_LENGTH_EXCEEDS_NOT(
        "Expected string[\"{2}\"] to have length ≤ value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length <= (length as Int)
                else -> false
            }
        }),
    STRING_LENGTH_IS("Expected string[\"{2}\"] to have length equal to value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length == (length as Int)
                else -> false
            }
        }),
    STRING_LENGTH_IS_NOT("Expected string[\"{2}\"] to have length not equal to value[{3}].",
        { actual: Any?, length: Any? ->
            when (actual as String) {
                actual -> actual.length != (length as Int)
                else -> false
            }
        });

    fun getMessage(className: String, fieldName: String, value1: Any?, value2: Any?): String {
        return CommonVerifier.ASSERTION_FAILED_FIELD_SINGLE.appendContext(
            message,
            className,
            fieldName,
            value1?.toString(),
            value2?.toString()
        )
    }

    fun getMessage(className: String, fieldName1: String, fieldName2: String, value1: Any?, value2: Any?): String {
        return CommonVerifier.ASSERTION_FAILED_FIELDS_DOUBLE.appendContext(
            message,
            className,
            fieldName1,
            fieldName2,
            value1?.toString(),
            value2?.toString()
        )
    }

    fun check(value1: Any?, value2: Any?): Boolean {
        return !predicate(value1, value2)
    }
}
