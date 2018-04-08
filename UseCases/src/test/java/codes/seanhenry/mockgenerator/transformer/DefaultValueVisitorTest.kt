package codes.seanhenry.mockgenerator.transformer

import codes.seanhenry.mockgenerator.ast.*
import junit.framework.TestCase

class DefaultValueVisitorTest : TestCase() {

  fun testShouldReturnEmptyStringWhenUnknownType() {
    assertNull(getDefaultValue("Unknown"))
  }

  fun testWhenKnownType() {
    assertEquals("0", getDefaultValue("Double"))
    assertEquals("0", getDefaultValue("Float"))
    assertEquals("0", getDefaultValue("Int"))
    assertEquals("0", getDefaultValue("Int16"))
    assertEquals("0", getDefaultValue("Int32"))
    assertEquals("0", getDefaultValue("Int64"))
    assertEquals("0", getDefaultValue("Int8"))
    assertEquals("0", getDefaultValue("UInt"))
    assertEquals("0", getDefaultValue("UInt16"))
    assertEquals("0", getDefaultValue("UInt32"))
    assertEquals("0", getDefaultValue("UInt64"))
    assertEquals("0", getDefaultValue("UInt8"))
    assertEquals("false", getDefaultValue("Bool"))
    assertEquals("\"!\"", getDefaultValue("UnicodeScalar"))
    assertEquals("\"!\"", getDefaultValue("Character"))
    assertEquals("\"\"", getDefaultValue("StaticString"))
    assertEquals("\"\"", getDefaultValue("String"))
  }

  fun testWhenKnownGenericType() {
    assertEquals("[]", getDefaultValueForGeneric("Array"))
    assertEquals("[]", getDefaultValueForGeneric("ArraySlice"))
    assertEquals("[]", getDefaultValueForGeneric("ContiguousArray"))
    assertEquals("[]", getDefaultValueForGeneric("Set"))
    assertEquals("[:]", getDefaultValueForGeneric("Dictionary"))
    assertEquals("[:]", getDefaultValueForGeneric("DictionaryLiteral"))
    assertEquals("nil", getDefaultValueForGeneric("Optional"))
  }

  fun testWhenSimpleFunction() {
    val function = FunctionType.Builder().build()
    assertEquals("{ }", getDefaultValue(function))
  }

  fun testWhenFunctionWithArguments() {
    val function = FunctionType.Builder()
        .argument("A")
        .argument("B")
        .build()
    assertEquals("{ _, _ in }", getDefaultValue(function))
  }

  fun testWhenFunctionWithKnownReturnType() {
    val function = FunctionType.Builder()
        .returnType("Int")
        .build()
    assertEquals("{ return 0 }", getDefaultValue(function))
  }

  fun testWhenFunctionHasUnknownReturnValueShouldNotHaveDefaultValue() {
    val function = FunctionType.Builder()
        .returnType("Unknown")
        .build()
    assertNull(getDefaultValue(function))
  }

  fun testWhenOptional() {
    val optional = OptionalType.Builder()
        .type("Any")
        .build()
    assertEquals("nil", getDefaultValue(optional))
  }

  fun testWhenBracketKnownType() {
    val type = BracketType(Type("Int"))
    assertEquals("0", getDefaultValue(type))
  }

  fun testWhenArray() {
    val type = ArrayType.Builder().type("Any").build()
    assertEquals("[]", getDefaultValue(type))
  }

  fun testWhenDictionary() {
    val type = DictionaryType.Builder()
        .keyType("Any")
        .valueType("Any")
        .build()
    assertEquals("[:]", getDefaultValue(type))
  }

  private fun getDefaultValueForGeneric(type: String): String? {
    return getDefaultValue(GenericType.Builder(type).build())
  }

  private fun getDefaultValue(type: String): String? {
    return getDefaultValue(Type(type))
  }

  private fun getDefaultValue(type: Type): String? {
    val visitor = DefaultValueVisitor()
    type.accept(visitor)
    return visitor.defaultValue
  }
}
