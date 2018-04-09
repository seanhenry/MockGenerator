package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class OptionalTypeTest: TestCase() {

  fun testShouldDeepCopy() {
    val original = OptionalType.Builder().type("Type").build()
    val copied = original.deepCopy()
    assertTrue(original == copied)
    assertFalse(original === copied)
    assertTrue(original.type == copied.type)
    assertFalse(original.type === copied.type)
  }
}
