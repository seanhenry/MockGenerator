package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class TupleTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = TupleType(listOf(TupleType.TupleElement("a", TypeIdentifier("A"))))
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.types[0] === copied.types[0])
    assertFalse(original.labels[0] === copied.labels[0])
  }

  fun testSurroundsTypeWithBrackets() {
    val type = TupleType(listOf(TupleType.TupleElement(null, TypeIdentifier("A"))))
    assertEquals("(A)", type.text)
  }
}
