package eli.solar

import eli.solar.exceptions.TestVerifierException
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class VerifyingFailureMessagesTest {

    @Test
    fun forMultiple() {
        val exception = assertThrows<TestVerifierException>("Validating multiple failure workflow") {
            verifying(this::class) {
                ifTrue("field1", false)
                ifFalse("field2", true)
            }.elseThrow(::TestVerifierException)
        }
        assertEquals(
            """ |
                |Assertion failed for field: "VerifyingFailureMessagesTest#field1";	Expected condition for value[false] to be true.;
                |Assertion failed for field: "VerifyingFailureMessagesTest#field2";	Expected condition for value[true] to be false.
            """.trimMargin(), exception.message
        )
    }

    @Test
    fun forIsTrue() {
        val exception = assertThrows<TestVerifierException>("") {
            verifying(this::class) {
                ifTrue("field1", false)
            }.elseThrow(::TestVerifierException)
        }
        assertEquals(
            """ |
                |Assertion failed for field: "VerifyingFailureMessagesTest#field1";	Expected condition for value[false] to be true.
            """.trimMargin(), exception.message
        )
    }

    @Test
    fun forIsFalse() {
        val exception = assertThrows<TestVerifierException>("") {
            verifying(this::class) {
                ifFalse("field1", true)
            }.elseThrow(::TestVerifierException)
        }
        assertEquals(
            """ |
                |Assertion failed for field: "VerifyingFailureMessagesTest#field1";	Expected condition for value[true] to be false.
            """.trimMargin(), exception.message
        )
    }
}