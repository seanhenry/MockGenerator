package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DefaultValueStoreTest  {

  @Test
  fun testIsZeroWhenTypeIsANumber() {
    assertDefaultValue("0", "Double")
    assertDefaultValue("0", "Float")
    assertDefaultValue("0", "Int")
    assertDefaultValue("0", "Int16")
    assertDefaultValue("0", "Int32")
    assertDefaultValue("0", "Int64")
    assertDefaultValue("0", "Int8")
    assertDefaultValue("0", "UInt")
    assertDefaultValue("0", "UInt16")
    assertDefaultValue("0", "UInt32")
    assertDefaultValue("0", "UInt64")
    assertDefaultValue("0", "UInt8")
  }

  @Test
  fun testIsFalseForBool() {
    assertDefaultValue("false", "Bool")
  }

  @Test
  fun testIsArrayLiteralForArrayTypes() {
    assertDefaultValue("[]", "Array")
    assertDefaultValue("[]", "ArraySlice")
    assertDefaultValue("[]", "ContiguousArray")
    assertDefaultValue("[]", "Set")
  }

  @Test
  fun testIsArrayLiteralForShortHandArrayTypes() {
    assertDefaultValue("[]", "[Type]")
    assertDefaultValue("[]", " [ Type ] ")
    assertDefaultValue("[]", " [ Type1 ] ")
    assertDefaultValue(null, "([Type], Void)")
  }

  @Test
  fun testIsDictionaryLiteralForDictionaryTypes() {
    assertDefaultValue("[:]", "Dictionary")
    assertDefaultValue("[:]", "DictionaryLiteral")
  }

  @Test
  fun testIsDictionaryLiteralForShortHandDictionaryTypes() {
    assertDefaultValue("[:]", "[Type:Type]")
    assertDefaultValue("[:]", " [ Type : Type ] ")
    assertDefaultValue("[:]", " [ Type1 : Type2 ] ")
    assertDefaultValue(null, "([Type : Type], Void)")
  }

  @Test
  fun testStripsGenericFromType() {
    assertDefaultValue("[]", "Array<String>")
    assertDefaultValue("[]", "ArraySlice <Int>")
    assertDefaultValue("[]", "ContiguousArray   <(String, Int)>")
    assertDefaultValue("[]", "Set<>")
    assertDefaultValue("[:]", "Dictionary <Int, (String, Int)>")
    assertDefaultValue("[:]", "DictionaryLiteral<String>")
    assertDefaultValue("[]", "Array<Array<String>>")
  }

  @Test
  fun testIsEmptyStringWhenTypeIsAString() {
    assertDefaultValue("\"\"", "String")
    assertDefaultValue("\"\"", "StaticString")
  }

  @Test
  fun testIsCharacterForCharacterTypes() {
    assertDefaultValue("\"!\"", "UnicodeScalar")
    assertDefaultValue("\"!\"", "Character")
  }

  @Test
  fun testIsNullWhenTypeDoesNotHaveADefaultValue() {
    assertDefaultValue(null, "NSObject")
    assertDefaultValue(null, "Object")
  }

  @Test
  fun testIsNullWhenTypeIsOptional() {
    assertDefaultValue(null, "AnyType?")
    assertDefaultValue(null, "AnyType??")
    assertDefaultValue(null, "AnyType!?")
  }

  @Test
  fun testIsNullWhenTypeIsIUO() {
    assertDefaultValue(null, "AnyType!")
    assertDefaultValue(null, "AnyType?!")
    assertDefaultValue(null, "AnyType!!")
  }

  @Test
  fun testShouldPreferNullWhenUnderlyingTypeHasDefaultValue() {
    assertDefaultValue(null, "String!")
    assertDefaultValue(null, "Int?")
    assertDefaultValue(null, "[Int]?")
    assertDefaultValue(null, "[Type: Type]?")
  }

  private fun assertDefaultValue(expected: String?, type: String) {
    assertEquals(expected, DefaultValueStore().getDefaultValue(type))
  }
}
