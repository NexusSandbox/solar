package eli.solar

import eli.solar.exceptions.TestVerifierException
import org.junit.Test

class VerifyingSuccessTest {
    @Test
    fun forMultiple() {
        verifying(this::class) {
            ifTrue("field1", true)
            ifFalse("field2", false)
        }.thenThrow(::TestVerifierException)
    }

    @Test
    fun forIsTrue() {
        verifying(this::class) {
            ifTrue("field1", true)
        }.thenThrow(::TestVerifierException)
    }

    @Test
    fun forIsFalse() {
        verifying(this::class) {
            ifFalse("field1", false)
        }.thenThrow(::TestVerifierException)
    }
}