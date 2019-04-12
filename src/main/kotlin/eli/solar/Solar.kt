package eli.solar

import eli.solar.enums.UnaryVerifier
import eli.solar.exceptions.CompositeException
import kotlin.reflect.KClass

class Solar(private val className: String) {
    private val messages = mutableListOf<String>()

    fun ifTrue(fieldName: String, fieldValue: Boolean): Solar {
        if (UnaryVerifier.VALUE_TRUE.check(fieldValue)) {
            messages.add(UnaryVerifier.VALUE_TRUE.getMessage(className, fieldName, fieldValue))
        }
        return this
    }

    fun ifFalse(fieldName: String, fieldValue: Boolean): Solar {
        if (UnaryVerifier.VALUE_FALSE.check(fieldValue)) {
            messages.add(UnaryVerifier.VALUE_FALSE.getMessage(className, fieldName, fieldValue))
        }
        return this
    }

    fun thenThrow(exceptionizer: (Collection<String>) -> CompositeException) {
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