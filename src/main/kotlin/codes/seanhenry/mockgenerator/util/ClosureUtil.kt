package codes.seanhenry.mockgenerator.util

class ClosureUtil {
  companion object {
    fun isClosure(type: String): Boolean {
      return type.contains("->")
    }

    fun surroundClosure(type: String): String {
      if (!isClosure(type) || isClosureSurrounded(type)) {
        return type
      }
      return "($type)"
    }

    private fun isClosureSurrounded(type: String) = type.replace(" ", "").startsWith("((")
  }
}
