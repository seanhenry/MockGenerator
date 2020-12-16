package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OptionalTypeBuilderTest {

  @Test
  fun testBuildOptionalType() {
    val optional = OptionalType.Builder().type("Type").build()
    assertEquals("Type?", optional.text)
    assertEquals("Type", optional.type.text)
  }

  @Test
  fun testWrapOptionalFunctionType() {
    val optional = OptionalType.Builder().type().function {}.build()
    assertEquals("(() -> ())?", optional.text)
  }

  @Test
  fun testBuildOptionalArrayType() {
    val optional = OptionalType.Builder().type().array {}.build()
    assertEquals("[]?", optional.text)
  }

  @Test
  fun testBuildIUOType() {
    val optional = OptionalType.Builder().type("Type").unwrapped().build()
    assertEquals("Type!", optional.text)
    assertTrue(optional.isImplicitlyUnwrapped)
  }

  @Test
  fun testBuildVerboseOptionalType() {
    val optional = OptionalType.Builder().type("Type").verbose().build()
    assertEquals("Optional<Type>", optional.text)
  }

  @Test
  fun testBuildAlreadyBuiltType() {
    val optional = OptionalType.Builder().type(TypeIdentifier.EMPTY_TUPLE).build()
    assertEquals(TypeIdentifier.EMPTY_TUPLE, optional.type)
  }
}
