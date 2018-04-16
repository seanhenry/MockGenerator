package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class TupleTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = TupleType(listOf(TypeIdentifier("Type")))
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.elements[0] === copied.elements[0])
  }

  fun testSurroundsTypeWithBrackets() {
    val type = TupleType(listOf(TypeIdentifier("Type")))
    assertEquals("(Type)", type.text)
  }
}
