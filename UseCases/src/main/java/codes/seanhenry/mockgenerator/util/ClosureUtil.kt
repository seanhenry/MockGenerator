package codes.seanhenry.mockgenerator.util

class ClosureUtil {
  companion object {
    fun isClosure(type: String): Boolean {
      return type.contains("->")
    }
  }
}
