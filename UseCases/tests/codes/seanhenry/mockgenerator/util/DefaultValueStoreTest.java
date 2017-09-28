package codes.seanhenry.mockgenerator.util;

import junit.framework.TestCase;

public class DefaultValueStoreTest extends TestCase {

  public void testIsZeroWhenTypeIsANumber() throws Exception {
    assertDefaultValue("0", "Double");
    assertDefaultValue("0", "Float");
    assertDefaultValue("0", "Int");
    assertDefaultValue("0", "Int16");
    assertDefaultValue("0", "Int32");
    assertDefaultValue("0", "Int64");
    assertDefaultValue("0", "Int8");
    assertDefaultValue("0", "UInt");
    assertDefaultValue("0", "UInt16");
    assertDefaultValue("0", "UInt32");
    assertDefaultValue("0", "UInt64");
    assertDefaultValue("0", "UInt8");
  }

  public void testIsFalseForBool() throws Exception {
    assertDefaultValue("false", "Bool");
  }

  public void testIsArrayLiteralForArrayTypes() throws Exception {
    assertDefaultValue("[]", "Array");
    assertDefaultValue("[]", "ArraySlice");
    assertDefaultValue("[]", "ContiguousArray");
    assertDefaultValue("[]", "Set");
  }

  public void testIsArrayLiteralForShortHandArrayTypes() throws Exception {
    assertDefaultValue("[]", "[Type]");
    assertDefaultValue("[]", " [ Type ] ");
    assertDefaultValue("[]", " [ Type1 ] ");
    assertDefaultValue(null, "([Type], Void)");
  }
  public void testIsDictionaryLiteralForDictionaryTypes() throws Exception {
    assertDefaultValue("[:]", "Dictionary");
    assertDefaultValue("[:]", "DictionaryLiteral");
  }

  public void testIsDictionaryLiteralForShortHandDictionaryTypes() throws Exception {
    assertDefaultValue("[:]", "[Type:Type]");
    assertDefaultValue("[:]", " [ Type : Type ] ");
    assertDefaultValue("[:]", " [ Type1 : Type2 ] ");
    assertDefaultValue(null, "([Type : Type], Void)");
  }

  public void testStripsGenericFromType() throws Exception {
    assertDefaultValue("[]", "Array<String>");
    assertDefaultValue("[]", "ArraySlice <Int>");
    assertDefaultValue("[]", "ContiguousArray   <(String, Int)>");
    assertDefaultValue("[]", "Set<>");
    assertDefaultValue("[:]", "Dictionary <Int, (String, Int)>");
    assertDefaultValue("[:]", "DictionaryLiteral<String>");
  }

  public void testIsEmptyStringWhenTypeIsAString() throws Exception {
    assertDefaultValue("\"\"", "String");
    assertDefaultValue("\"\"", "StaticString");
  }

  public void testIsCharacterForCharacterTypes() throws Exception {
    assertDefaultValue("\"!\"", "UnicodeScalar");
    assertDefaultValue("\"!\"", "Character");
  }

  public void testIsNullWhenTypeDoesNotHaveADefaultValue() throws Exception {
    assertDefaultValue(null, "NSObject");
    assertDefaultValue(null, "Object");
  }

  public void testIsNullWhenTypeIsOptional() throws Exception {
    assertDefaultValue(null, "AnyType?");
    assertDefaultValue(null, "AnyType??");
    assertDefaultValue(null, "AnyType!?");
  }

  public void testIsNullWhenTypeIsIUO() throws Exception {
    assertDefaultValue(null, "AnyType!");
    assertDefaultValue(null, "AnyType?!");
    assertDefaultValue(null, "AnyType!!");
  }

  public void testShouldPreferNullWhenUnderlyingTypeHasDefaultValue() throws Exception {
    assertDefaultValue(null, "String!");
    assertDefaultValue(null, "Int?");
    assertDefaultValue(null, "[Int]?");
    assertDefaultValue(null, "[Type: Type]?");
  }

  private void assertDefaultValue(String expected, String type) {
    assertEquals(expected, new DefaultValueStore().getDefaultValue(type));
  }
}
