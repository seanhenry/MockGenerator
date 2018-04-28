package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class TypeIdentifierBuilderTest: TestCase() {

  fun testShouldBuildType() {
    val type = TypeIdentifier.Builder("Type").build()
    assertEquals("Type", type.text)
  }

  fun testShouldBuildNestedTypes() {
    val type = TypeIdentifier.Builder("A")
        .nest("B")
        .build()
    assertEquals("A.B", type.text)
  }
}
