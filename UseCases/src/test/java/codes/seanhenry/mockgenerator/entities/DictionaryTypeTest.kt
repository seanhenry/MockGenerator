package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.DictionaryType
import junit.framework.TestCase

class DictionaryTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = DictionaryType.Builder()
        .keyType("Type")
        .valueType("Type")
        .build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.keyType === copied.keyType)
    assertFalse(original.valueType === copied.valueType)
  }
}
