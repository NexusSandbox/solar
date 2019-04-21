package eli.solar

import eli.solar.enums.BinaryVerifier
import eli.solar.enums.TernaryVerifier
import eli.solar.enums.UnaryVerifier
import eli.solar.exceptions.AbstractCompositeException
import java.time.temporal.Temporal
import kotlin.reflect.KClass

class Solar(private val className: String) {
    private val messages = mutableListOf<String>()

    private fun checkUnaryVerifierField(verifier: UnaryVerifier, fieldName: String, fieldValue: Any?): Solar {
        if (verifier.check(fieldValue)) {
            messages.add(verifier.getMessage(className, fieldName, fieldValue))
        }
        return this
    }

    fun ifTrue(fieldName: String, fieldValue: Boolean): Solar {
        return checkUnaryVerifierField(UnaryVerifier.VALUE_TRUE, fieldName, fieldValue)
    }

    fun ifFalse(fieldName: String, fieldValue: Boolean): Solar {
        return checkUnaryVerifierField(UnaryVerifier.VALUE_FALSE, fieldName, fieldValue)
    }

    fun ifNull(fieldName: String, fieldValue: Any?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.VALUE_NULL, fieldName, fieldValue)
    }

    fun ifNotNull(fieldName: String, fieldValue: Any?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.VALUE_NULL_NOT, fieldName, fieldValue)
    }

    fun ifStringIsBlank(fieldName: String, fieldValue: String?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.STRING_BLANK, fieldName, fieldValue)
    }

    fun ifStringIsNotBlank(fieldName: String, fieldValue: String?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.STRING_BLANK_NOT, fieldName, fieldValue)
    }

    fun ifStringIsEmpty(fieldName: String, fieldValue: String?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.STRING_EMPTY, fieldName, fieldValue)
    }

    fun ifStringIsNotEmpty(fieldName: String, fieldValue: String?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.STRING_EMPTY_NOT, fieldName, fieldValue)
    }

    fun ifCollectionIsEmpty(fieldName: String, fieldValue: Collection<*>?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.COLLECTION_EMPTY, fieldName, fieldValue)
    }

    fun ifCollectionIsNotEmpty(fieldName: String, fieldValue: Collection<*>?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.COLLECTION_EMPTY_NOT, fieldName, fieldValue)
    }

    fun ifCollectionContainsNullValues(fieldName: String, fieldValue: Collection<*>?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.COLLECTION_CONTAINS_NULL_VALUES, fieldName, fieldValue)
    }

    fun ifCollectionContainsNoNullValues(fieldName: String, fieldValue: Collection<*>?): Solar {
        return checkUnaryVerifierField(UnaryVerifier.COLLECTION_CONTAINS_NULL_VALUES_NOT, fieldName, fieldValue)
    }

    private fun checkBinaryVerifierField(
        verifier: BinaryVerifier,
        fieldName: String,
        fieldValue1: Any?,
        fieldValue2: Any?
    ): Solar {
        if (verifier.check(fieldValue1, fieldValue2)) {
            messages.add(verifier.getMessage(className, fieldName, fieldValue1, fieldValue2))
        }
        return this
    }

    private fun checkBinaryVerifierFields(
        verifier: BinaryVerifier,
        fieldName1: String,
        fieldName2: String,
        fieldValue1: Any?,
        fieldValue2: Any?
    ): Solar {
        if (verifier.check(fieldValue1, fieldValue2)) {
            messages.add(verifier.getMessage(className, fieldName1, fieldName2, fieldValue1, fieldValue2))
        }
        return this
    }

    fun ifEitherAreTrue(fieldName1: String, fieldName2: String, fieldValue1: Boolean, fieldValue2: Boolean): Solar {
        return checkBinaryVerifierFields(BinaryVerifier.VALUE_EITHER, fieldName1, fieldName2, fieldValue1, fieldValue2)
    }

    fun ifEitherAreFalse(fieldName1: String, fieldName2: String, fieldValue1: Boolean, fieldValue2: Boolean): Solar {
        return checkBinaryVerifierFields(BinaryVerifier.VALUE_EITHER_NOT, fieldName1, fieldName2, fieldValue1, fieldValue2)
    }

    fun ifEitherAreNull(fieldName1: String, fieldName2: String, fieldValue1: Any?, fieldValue2: Any?): Solar {
        return checkBinaryVerifierFields(BinaryVerifier.VALUE_EITHER_NULL, fieldName1, fieldName2, fieldValue1, fieldValue2)
    }

    fun ifEitherAreNotNull(fieldName1: String, fieldName2: String, fieldValue1: Any?, fieldValue2: Any?): Solar {
        return checkBinaryVerifierFields(BinaryVerifier.VALUE_EITHER_NULL_NOT, fieldName1, fieldName2, fieldValue1, fieldValue2)
    }

    fun ifCollectionContainsAllValues(fieldName: String, actualValues: Collection<*>, expectedValues: Collection<*>): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_CONTAINS_ALL_VALUES, fieldName, actualValues, expectedValues)
    }

    fun ifCollectionContainsAnyValues(fieldName: String, actualValues: Collection<*>, expectedValues: Collection<*>): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_CONTAINS_ANY_VALUES, fieldName, actualValues, expectedValues)
    }

    fun ifCollectionContainsNoValues(fieldName: String, actualValues: Collection<*>, expectedValues: Collection<*>): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_CONTAINS_NO_VALUES, fieldName, actualValues, expectedValues)
    }

    fun ifCollectionsEqual(fieldName: String, actualValue: Collection<*>?, expectedValue: Collection<*>?): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_EQUALS, fieldName, actualValue, expectedValue)
    }

    fun ifCollectionsDoNotEqual(fieldName: String, actualValue: Collection<*>?, expectedValue: Collection<*>?): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_EQUALS_NOT, fieldName, actualValue, expectedValue)
    }

    fun ifValuesEqual(fieldName: String, actualValue: Any?, expectedValue: Any?): Solar {
        return checkBinaryVerifierField(BinaryVerifier.VALUE_EQUALS, fieldName, actualValue, expectedValue)
    }

    fun ifValuesDoNotEqual(fieldName: String, actualValue: Any?, expectedValue: Any?): Solar {
        return checkBinaryVerifierField(BinaryVerifier.VALUE_EQUALS_NOT, fieldName, actualValue, expectedValue)
    }

    fun ifStringMatches(fieldName: String, actualValue: String?, pattern: String): Solar {
        return checkBinaryVerifierField(BinaryVerifier.STRING_MATCHES, fieldName, actualValue, pattern)
    }

    fun ifStringDoesNotMatch(fieldName: String, actualValue: String?, pattern: String): Solar {
        return checkBinaryVerifierField(BinaryVerifier.STRING_MATCHES_NOT, fieldName, actualValue, pattern)
    }

    fun ifCollectionMatchesAll(fieldName: String, actualValue: Collection<*>?, predicate: (Any) -> Boolean): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_MATCHES_ALL, fieldName, actualValue, predicate)
    }

    fun ifCollectionMatchesAny(fieldName: String, actualValue: Collection<*>?, predicate: (Any) -> Boolean): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_MATCHES_ANY, fieldName, actualValue, predicate)
    }

    fun ifCollectionMatchesNone(fieldName: String, actualValue: Collection<*>?, predicate: (Any) -> Boolean): Solar {
        return checkBinaryVerifierField(BinaryVerifier.COLLECTION_MATCHES_NONE, fieldName, actualValue, predicate)
    }

    private fun checkTernaryVerifierField(
        verifier: TernaryVerifier,
        fieldName: String,
        fieldValue1: Any?,
        fieldValue2: Any?,
        fieldValue3: Any?
    ): Solar {
        if (verifier.check(fieldValue1, fieldValue2, fieldValue3)) {
            messages.add(verifier.getMessage(className, fieldName, fieldValue1, fieldValue2, fieldValue3))
        }
        return this
    }

    fun <T: Number> ifValuesEqualWithError(fieldName: String, actualValue: T, expectedValue: T, error: T): Solar {
        return checkTernaryVerifierField(TernaryVerifier.NUMERIC_EQUAL_WITH_ERROR, fieldName, actualValue, expectedValue, error)
    }

    fun <T: Number> ifValuesDoNotEqualWithError(fieldName: String, actualValue: T, expectedValue: T, error: T): Solar {
        return checkTernaryVerifierField(TernaryVerifier.NUMERIC_EQUAL_NOT_WITH_ERROR, fieldName, actualValue, expectedValue, error)
    }

    fun <T: Temporal> ifTemporalEqualsWithTolerance(fieldName: String, actualValue: T, expectedValue: T, tolerance: Long): Solar {
        return checkTernaryVerifierField(TernaryVerifier.DATETIME_EQUAL_WITH_TOLERANCE, fieldName, actualValue, expectedValue, tolerance)
    }

    fun <T: Temporal> ifTemporalDoesNotEqualWithTolerance(fieldName: String, actualValue: T, expectedValue: T, tolerance: Long): Solar {
        return checkTernaryVerifierField(TernaryVerifier.DATETIME_EQUAL_NOT_WITH_TOLERANCE, fieldName, actualValue, expectedValue, tolerance)
    }

    fun elseThrow(exceptionizer: (Collection<String>) -> AbstractCompositeException) {
        if (messages.isNotEmpty()) {
            throw exceptionizer.invoke(messages)
        }
    }
}

fun verifying(classType: KClass<*>, init: Solar.() -> Unit): Solar {
    val solar = Solar(classType.simpleName.orEmpty())
    solar.init()

    return solar
}