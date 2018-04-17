package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import junit.framework.TestCase

class RemoveOptionalVisitorTest: TestCase() {

  fun testShouldRemoveOptional() {
    val optional = OptionalType.Builder()
        .type("A")
        .build()
    val transformed = RemoveOptionalVisitor.removeOptional(optional)
    assertEquals(optional.type, transformed)
  }

  fun testShouldReturnOriginalWhenNotOptional() {
    val type = TypeIdentifier.Builder("A").build()
    val transformed = RemoveOptionalVisitor.removeOptional(type)
    assertEquals(type, transformed)
  }
}
