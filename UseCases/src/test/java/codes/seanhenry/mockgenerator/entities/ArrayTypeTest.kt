package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.ArrayType
import junit.framework.TestCase

class ArrayTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = ArrayType.Builder()
        .type("Type")
        .build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertTrue(original.type == copied.type)
  }
}
