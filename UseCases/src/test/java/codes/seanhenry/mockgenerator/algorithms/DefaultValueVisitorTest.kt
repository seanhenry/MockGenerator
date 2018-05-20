package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
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

  fun testWhenKnownTypeSurroundedInBracket() {
    val type = TupleType.Builder().element("Int").build()
    assertEquals("0", getDefaultValue(type))
  }

  fun testWhenUnknownTypeSurroundedInBracket() {
    val type = TupleType.Builder().element("Unknown").build()
    assertNull(getDefaultValue(type))
  }

  fun testWhenEmptyTuple() {
    val type = TupleType.Builder().build()
    assertEquals("()", getDefaultValue(type))
  }

  fun testWhenTupleOfKnownValues() {
    val type = TupleType.Builder()
        .element("Int")
        .element("String")
        .build()
    assertEquals("(0, \"\")", getDefaultValue(type))
  }

  fun testWhenTupleOfMixedKnownAndUnknownValues() {
    val type = TupleType.Builder()
        .element("Int")
        .element("String")
        .element("Unknown")
        .build()
    assertNull(getDefaultValue(type))
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

  fun testWhenVoid() {
    val type = TypeIdentifier("Void")
    assertEquals("()", getDefaultValue(type))
  }

  fun testWhenVoidTuple() {
    val type = TupleType.Builder().element("Void").build()
    assertEquals("()", getDefaultValue(type))
  }

  fun testWhenEmpty() {
    val type = TypeIdentifier("")
    assertNull(getDefaultValue(type))
  }

  private fun getDefaultValueForGeneric(type: String): String? {
    return getDefaultValue(GenericType.Builder(type).build())
  }

  private fun getDefaultValue(type: String): String? {
    return getDefaultValue(TypeIdentifier(type))
  }

  private fun getDefaultValue(type: Type): String? {
    return DefaultValueVisitor.getDefaultValue(type)
  }
}
