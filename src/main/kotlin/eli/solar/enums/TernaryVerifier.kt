package eli.solar.enums

import java.time.*

internal enum class TernaryVerifier(
    private val message: String,
    private val predicate: Function3<Any?, Any?, Any?, Boolean>
) {
    NUMERIC_EQUAL_WITH_ERROR("Expected value[{2}] ≡ value[{3}] ± ε[{4}].",
        { actual: Any?, expected: Any?, epsilon: Any? ->
            when (expected) {
                (expected is Double) -> Math.abs((expected as Double) - (actual as Double)) <= (epsilon as Double)
                (expected is Float) -> Math.abs((expected as Float) - (actual as Float)) <= (epsilon as Float)
                (expected is Long) -> Math.abs((expected as Long) - (actual as Long)) <= (epsilon as Long)
                (expected is Int) -> Math.abs((expected as Int) - (actual as Int)) <= (epsilon as Int)
                (expected is Short) -> Math.abs((expected as Short) - (actual as Short)) <= (epsilon as Short)
                (expected is Byte) -> Math.abs((expected as Byte) - (actual as Byte)) <= (epsilon as Byte)
                else -> false
            }

        }),
    NUMERIC_EQUAL_NOT_WITH_ERROR("Expected value[{2}] ≠ value[{3}] ± ε[{4}].",
        { actual: Any?, expected: Any?, epsilon: Any? ->
            when (expected) {
                (expected is Double) -> Math.abs((expected as Double) - (actual as Double)) > (epsilon as Double)
                (expected is Float) -> Math.abs((expected as Float) - (actual as Float)) > (epsilon as Float)
                (expected is Long) -> Math.abs((expected as Long) - (actual as Long)) > (epsilon as Long)
                (expected is Int) -> Math.abs((expected as Int) - (actual as Int)) > (epsilon as Int)
                (expected is Short) -> Math.abs((expected as Short) - (actual as Short)) > (epsilon as Short)
                (expected is Byte) -> Math.abs((expected as Byte) - (actual as Byte)) > (epsilon as Byte)
                else -> false
            }
        }),
    DATETIME_EQUAL_WITH_TOLERANCE("Expected value[{2}] ≡ value[{3}] ± ε[{4} s].",
        { actual: Any?, expected: Any?, tolerance: Any? ->
            val zoneOffset: ZoneOffset = ZoneOffset.UTC
            val localDate: LocalDate = LocalDate.now(zoneOffset.normalized())
            when (expected) {
                (expected is LocalDate) -> Math.abs((expected as LocalDate).toEpochDay() - (actual as LocalDate).toEpochDay()) <= (tolerance as Long)
                (expected is LocalTime) -> Math.abs(
                    (expected as LocalTime).toEpochSecond(
                        localDate,
                        zoneOffset
                    ) - (actual as LocalTime).toEpochSecond(localDate, zoneOffset)
                ) <= (tolerance as Long)
                (expected is LocalDateTime) -> Math.abs(
                    (expected as LocalDateTime).toEpochSecond(zoneOffset) - (actual as LocalDateTime).toEpochSecond(
                        zoneOffset
                    )
                ) <= (tolerance as Long)
                (expected is ZonedDateTime) -> Math.abs((expected as ZonedDateTime).toEpochSecond() - (actual as ZonedDateTime).toEpochSecond()) <= (tolerance as Long)
                (expected is Instant) -> true
                else -> false
            }
        }),
    DATETIME_EQUAL_NOT_WITH_TOLERANCE(
        "Expected value[{2}] ≠ value[{3}] ± ε[{4} s].",
        { actual: Any?, expected: Any?, tolerance: Any? ->
            val zoneOffset: ZoneOffset = ZoneOffset.UTC
            val localDate: LocalDate = LocalDate.now(zoneOffset.normalized())
            when (expected) {
                (expected is LocalDate) -> Math.abs((expected as LocalDate).toEpochDay() - (actual as LocalDate).toEpochDay()) > (tolerance as Long)
                (expected is LocalTime) -> Math.abs(
                    (expected as LocalTime).toEpochSecond(
                        localDate,
                        zoneOffset
                    ) - (actual as LocalTime).toEpochSecond(localDate, zoneOffset)
                ) > (tolerance as Long)
                (expected is LocalDateTime) -> Math.abs(
                    (expected as LocalDateTime).toEpochSecond(zoneOffset) - (actual as LocalDateTime).toEpochSecond(
                        zoneOffset
                    )
                ) > (tolerance as Long)
                (expected is ZonedDateTime) -> Math.abs((expected as ZonedDateTime).toEpochSecond() - (actual as ZonedDateTime).toEpochSecond()) > (tolerance as Long)
                (expected is Instant) -> true
                else -> false
            }

        }),
    STRING_LENGTH_BETWEEN(
        "Expected string[\"{2}\"] to have length between value[{3}] and value[{4}].",
        { actual: Any?, minLength: Any?, maxLength: Any? ->
            when (minLength) {
                (minLength as Long) -> (actual as String).length >= minLength && actual.length <= (maxLength as Long)
                (minLength as Int) -> (actual as String).length >= minLength && actual.length <= (maxLength as Int)
                (minLength as Short) -> (actual as String).length >= minLength && actual.length <= (maxLength as Short)
                (minLength as Byte) -> (actual as String).length >= minLength && actual.length <= (maxLength as Byte)
                else -> false
            }
        }),
    STRING_LENGTH_BETWEEN_NOT(
        "Expected string[\"{2}\"] to have length outside value[{3}] and value[{4}].",
        { actual: Any?, minLength: Any?, maxLength: Any? ->
            when (minLength) {
                (minLength as Long) -> (actual as String).length < minLength && actual.length > (maxLength as Long)
                (minLength as Int) -> (actual as String).length < minLength && actual.length > (maxLength as Int)
                (minLength as Short) -> (actual as String).length < minLength && actual.length > (maxLength as Short)
                (minLength as Byte) -> (actual as String).length < minLength && actual.length > (maxLength as Byte)
                else -> false
            }
        });

    fun getMessage(className: String, fieldName: String, value1: Any?, value2: Any?, value3: Any?): String {
        return CommonVerifier.ASSERTION_FAILED_FIELD_SINGLE.appendContext(
            message,
            className,
            fieldName,
            value1?.toString(),
            value2?.toString(),
            value3?.toString()
        )
    }

    fun getMessage(
        className: String,
        fieldName1: String,
        fieldName2: String,
        value1: Any?,
        value2: Any?,
        value3: Any?
    ): String {
        return CommonVerifier.ASSERTION_FAILED_FIELDS_DOUBLE.appendContext(
            message,
            className,
            fieldName1,
            fieldName2,
            value1?.toString(),
            value2?.toString(),
            value3?.toString()
        )
    }

    fun check(value1: Any?, value2: Any?, value3: Any?): Boolean {
        return !predicate(value1, value2, value3)
    }
}