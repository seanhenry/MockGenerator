package codes.seanhenry.mockgenerator.util

class DefaultValueStore {

  private val defaultValues: Map<String, String> = mapOf(
      Pair("Double", "0"),
      Pair("Float", "0"),
      Pair("Int", "0"),
      Pair("Int16", "0"),
      Pair("Int32", "0"),
      Pair("Int64", "0"),
      Pair("Int8", "0"),
      Pair("UInt", "0"),
      Pair("UInt16", "0"),
      Pair("UInt32", "0"),
      Pair("UInt64", "0"),
      Pair("UInt8", "0"),
      Pair("Array", "[]"),
      Pair("ArraySlice", "[]"),
      Pair("ContiguousArray", "[]"),
      Pair("Set", "[]"),
      Pair("Bool", "false"),
      Pair("Dictionary", "[:]"),
      Pair("DictionaryLiteral", "[:]"),
      Pair("UnicodeScalar", "\"!\""),
      Pair("Character", "\"!\""),
      Pair("StaticString", "\"\""),
      Pair("String", "\"\"")
  )

  fun getDefaultValue(typeName: String): String? {
    var trimmed = typeName.replace("\\s".toRegex(), "")
    trimmed = trimmed.replace("<.*>".toRegex(), "")
    if (OptionalUtil.isOptional(trimmed)) {
      return null
    } else if (isArray(trimmed)) {
      return "[]"
    } else if (isDictionary(trimmed)) {
      return "[:]"
    }
    return defaultValues[trimmed]
  }

  private fun isArray(typeName: String): Boolean {
    return typeName.matches("\\[[\\w0-9]+\\]".toRegex())
  }

  private fun isDictionary(typeName: String): Boolean {
    return typeName.matches("\\[[\\w0-9]+:[\\w0-9]+\\]".toRegex())
  }
}
