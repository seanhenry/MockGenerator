package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import junit.framework.TestCase

class OptionalizeIUOVisitorTest: TestCase() {

  fun testShouldRemoveIUOAndReplaceWithOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .unwrapped()
        .build()
    assertEquals("A?", OptionalizeIUOVisitor.optionalize(optional).text)
  }

  fun testShouldRemoveNotChangeOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .verbose()
        .build()
    assertEquals(optional, OptionalizeIUOVisitor.optionalize(optional))
  }

  fun testShouldRemoveNotChangeOtherType() {
    val type = TypeIdentifier.Builder("B")
        .build()
    assertEquals(type, OptionalizeIUOVisitor.optionalize(type))
  }
}