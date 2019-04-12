package eli.solar.exceptions

open class CompositeException protected constructor(messages: Collection<String>) : RuntimeException(joiner(messages)) {

    companion object {
        private fun joiner(messages: Collection<String>): String {
            return messages.joinToString(separator = ";\n", prefix = "\n")
        }
    }
}