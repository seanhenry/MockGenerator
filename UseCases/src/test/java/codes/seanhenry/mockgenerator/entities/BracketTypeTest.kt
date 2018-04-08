package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.BracketType
import codes.seanhenry.mockgenerator.ast.TypeIdentifier
import junit.framework.TestCase

class BracketTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = BracketType(TypeIdentifier("Type"))
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertFalse(original.type === copied.type)
  }
}
