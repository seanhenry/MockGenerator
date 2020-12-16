package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.FunctionType
import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.TupleType
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SurroundOptionalVisitorTest  {

  @Test
  fun testShouldSurroundType() {
    val type = TypeIdentifier.Builder("A").build()
    val transformed = SurroundOptionalVisitor.surround(type, false)
    assertTrue(transformed is OptionalType)
    val optional = transformed as OptionalType
    assertEquals(type, optional.type)
  }

  @Test
  fun testShouldSurroundFunctionTypeWithTupleToo() {
    val type = FunctionType.Builder().build()
    val transformed = SurroundOptionalVisitor.surround(type, false)
    assertTrue(transformed is OptionalType)
    val optional = transformed as OptionalType
    assertTrue(optional.type is TupleType)
    val tuple = optional.type as TupleType
    assertEquals(1, tuple.types.size)
    assertEquals(type, tuple.types[0])
  }

  @Test
  fun testShouldSurroundTypeWithIUO() {
    val type = TypeIdentifier.Builder("A").build()
    val transformed = SurroundOptionalVisitor.surround(type, true)
    assertTrue(transformed is OptionalType)
    val optional = transformed as OptionalType
    assertTrue(optional.isImplicitlyUnwrapped)
  }

  @Test
  fun testShouldSurroundFunctionTypeWithTupleTooWithIUO() {
    val type = FunctionType.Builder().build()
    val transformed = SurroundOptionalVisitor.surround(type, true)
    assertTrue(transformed is OptionalType)
    val optional = transformed as OptionalType
    assertTrue(optional.isImplicitlyUnwrapped)
  }
}
