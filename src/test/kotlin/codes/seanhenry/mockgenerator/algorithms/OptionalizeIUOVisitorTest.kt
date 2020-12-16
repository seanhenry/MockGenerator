package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OptionalizeIUOVisitorTest {

  @Test
  fun testShouldRemoveIUOAndReplaceWithOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .unwrapped()
        .build()
    assertEquals("A?", OptionalizeIUOVisitor.optionalize(optional).text)
  }

  @Test
  fun testShouldRemoveNotChangeOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .verbose()
        .build()
    assertEquals(optional, OptionalizeIUOVisitor.optionalize(optional))
  }

  @Test
  fun testShouldRemoveNotChangeOtherType() {
    val type = TypeIdentifier.Builder("B")
        .build()
    assertEquals(type, OptionalizeIUOVisitor.optionalize(type))
  }
}