package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class BracketTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = BracketType(TypeIdentifier("Type"))
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.type === copied.type)
  }

  fun testSurroundsTypeWithBrackets() {
    val type = BracketType(TypeIdentifier("Type"))
    assertEquals("(Type)", type.text)
  }
}
