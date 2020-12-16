package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TypeIdentifierBuilderTest {

  @Test
  fun testShouldBuildType() {
    val type = TypeIdentifier.Builder("Type").build()
    assertEquals("Type", type.text)
  }

  @Test
  fun testShouldBuildNestedTypes() {
    val type = TypeIdentifier.Builder("A")
        .nest("B")
        .build()
    assertEquals("A.B", type.text)
  }
}
