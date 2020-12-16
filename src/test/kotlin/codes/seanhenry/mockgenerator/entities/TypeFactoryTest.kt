package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TypeFactoryTest {

  @Test
  fun testShouldBuildFunctionType() {
    val optional = OptionalType.Builder().type().function {}.build()
    assertEquals("(() -> ())?", optional.text)
  }

  @Test
  fun testShouldBuildOptionalType() {
    val optional = OptionalType.Builder().type().optional { it.type("Type") }.build()
    assertEquals("Type??", optional.text)
  }

  @Test
  fun testShouldBuildArrayType() {
    val optional = OptionalType.Builder().type().array { it.type("Type") }.build()
    assertEquals("[Type]?", optional.text)
  }

  @Test
  fun testShouldBuildTupleType() {
    val optional = OptionalType.Builder().type().bracket().type("Type").build()
    assertEquals("(Type)?", optional.text)
  }

  @Test
  fun testShouldBuildDictionaryType() {
    val optional = OptionalType.Builder()
        .type().dictionary {}
        .build()
    assertEquals("[: ]?", optional.text)
  }

  @Test
  fun testShouldBuildGenericType() {
    val optional = OptionalType.Builder()
        .type().generic("Type") {}
        .build()
    assertEquals("Type<>?", optional.text)
  }

  @Test
  fun testShouldBuildTypeIdentifier() {
    val optional = OptionalType.Builder()
        .type().typeIdentifier("A") { it.nest("B") }
        .build()
    assertEquals("A.B?", optional.text)
  }

  @Test
  fun testShouldBuildTuple() {
    val optional = OptionalType.Builder()
        .type().tuple { it.element("B") }
        .build()
    assertEquals("(B)?", optional.text)
  }
}
