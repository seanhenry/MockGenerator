package codes.seanhenry.mockgenerator.util

internal class OptionalUtil {

  companion object {
    fun removeOptional(type: String): String {
      if (endsWithOptional(type)) {
        return removeLast(type)
      }
      return type
    }

    @JvmStatic
    fun isOptional(type: String): Boolean {
      val trimmed = type.replace(Regex("\\s"), "")
      return trimmed.endsWith("?") || trimmed.endsWith("!") || trimmed.startsWith("Optional<")
    }

    fun removeOptionalRecursively(type: String): String {
      var modified = type
      while (endsWithOptional(modified)) {
        modified = removeLast(modified)
      }
      return modified
    }

    private fun endsWithOptional(string: String): Boolean {
      return string.endsWith("?") || string.endsWith("!")
    }

    private fun removeLast(string: String): String {
      return string.removeRange(string.length - 1, string.length)
    }
  }
}
