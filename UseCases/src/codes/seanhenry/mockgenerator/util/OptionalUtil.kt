package codes.seanhenry.mockgenerator.util

internal class OptionalUtil {

  companion object {
    fun removeOptional(type: String): String {
      var type = type
      while (type.endsWith("?") || type.endsWith("!")) {
        type = type.removeRange(type.length - 1, type.length)
      }
      return type
    }
    @JvmStatic
    fun isOptional(type: String): Boolean {
      val trimmed = type.replace(Regex("\\s"), "")
      return trimmed.endsWith("?") || trimmed.endsWith("!") || trimmed.startsWith("Optional<")
    }
  }
}
