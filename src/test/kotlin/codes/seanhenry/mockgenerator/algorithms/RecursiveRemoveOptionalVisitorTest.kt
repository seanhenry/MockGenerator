package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RecursiveRemoveOptionalVisitorTest {

  @Test
  fun testShouldRemoveOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .build()
    val transformed = RecursiveRemoveOptionalVisitor.removeOptional(optional)
    assertEquals(optional.type, transformed)
  }

  @Test
  fun testShouldRemoveDoubleOptional() {
    val optional = OptionalType.Builder()
        .type().optional { it.type("A") }
        .build()
    val transformed = RecursiveRemoveOptionalVisitor.removeOptional(optional)
    val innerOptional = optional.type as OptionalType
    assertEquals(innerOptional.type, transformed)
  }

  @Test
  fun testShouldReturnTypeWhenNotOptional() {
    val type = TypeIdentifier.Builder("A").build()
    val transformed = RecursiveRemoveOptionalVisitor.removeOptional(type)
    assertEquals(type, transformed)
  }
}
