package eli.solar

import eli.solar.exceptions.TestVerifierException
import org.junit.Test

class VerifyingSuccessTest {
    @Test
    fun forIfMultiple() {
        verifying(this::class) {
            ifTrue("field01", true)
            ifFalse("field02", false)
            ifNull("field03", null)
            ifNotNull("field04", Any())
            ifStringIsBlank("field05", " ")
            ifStringIsNotBlank("field06", "x")
            ifStringIsEmpty("field07", "")
            ifStringIsNotEmpty("field08", "x")
            ifCollectionIsEmpty("field09", listOf<Int>())
            ifCollectionIsNotEmpty("field10", listOf(1, 2, 3))
            ifCollectionContainsNullValues("field11", listOf(null))
            ifCollectionContainsNoNullValues("field12", listOf(1, 2, 3))
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfTrue() {
        verifying(this::class) {
            ifTrue("field1", true)
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfFalse() {
        verifying(this::class) {
            ifFalse("field1", false)
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfNull() {
        verifying(this::class) {
            ifNull("field1", null)
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfNotNull() {
        verifying(this::class) {
            ifNotNull("field1", Any())
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfStringIsBlank() {
        verifying(this::class) {
            ifStringIsBlank("field1", " ")
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfStringIsNotBlank() {
        verifying(this::class) {
            ifStringIsNotBlank("field1", "x")
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfStringIsEmpty() {
        verifying(this::class) {
            ifStringIsEmpty("field1", "")
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfStringIsNotEmpty() {
        verifying(this::class) {
            ifStringIsNotEmpty("field1", "x")
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfCollectionIsEmpty() {
        verifying(this::class) {
            ifCollectionIsEmpty("field1", listOf<Int>())
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfCollectionIsNotEmpty() {
        verifying(this::class) {
            ifCollectionIsNotEmpty("field1", listOf(1, 2, 3))
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfCollectionContainsNullValues() {
        verifying(this::class) {
            ifCollectionContainsNullValues("field1", listOf(null))
        }.elseThrow(::TestVerifierException)
    }

    @Test
    fun forIfCollectionContainsNoNullValues() {
        verifying(this::class) {
            ifCollectionContainsNoNullValues("field1", listOf(1, 2, 3))
        }.elseThrow(::TestVerifierException)
    }
}