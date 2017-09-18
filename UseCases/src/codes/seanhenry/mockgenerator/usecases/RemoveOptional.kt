package codes.seanhenry.mockgenerator.usecases

internal class RemoveOptional {

  companion object {
    fun removeOptional(type: String): String {
      var type = type
      while (type.endsWith("?") || type.endsWith("!")) {
        type = type.removeRange(type.length - 1, type.length)
      }
      return type
    }
  }
}
