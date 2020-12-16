package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.FunctionType
import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.generator.ClosureParameterViewModel
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FunctionParameterTransformerTest {

  @Test
  fun testShouldCapitalizeName() {
    assertEquals("Name", transformEmptyFunction().capitalizedName)
  }

  @Test
  fun testShouldPreserveName() {
    assertEquals("name", transformEmptyFunction().name)
  }

  @Test
  fun testShouldBeFalseWhenNoArguments() {
    assertFalse(transformEmptyFunction().hasArguments)
  }

  @Test
  fun testShouldBeTrueNoArguments() {
    assertTrue(transformSimpleFunction().hasArguments)
  }

  @Test
  fun testShouldTransformEmptyFunctionToEmptyTuple() {
    assertEquals("()", transformEmptyFunction().argumentsTupleRepresentation)
  }

  @Test
  fun testShouldTransformAppendVoidTo1ArgumentFunctionTuple() {
    assertEquals("(A, Void)", transformSimpleFunction().argumentsTupleRepresentation)
  }

  @Test
  fun testShouldTransformCreateTupleFrom2ArgumentFunctionTuple() {
    assertEquals("(A, B)", transform2ArgumentFunction().argumentsTupleRepresentation)
  }

  @Test
  fun testShouldTransformEmptyTupleIntoClosureCall() {
    assertEquals("name()", transformEmptyFunction().implicitClosureCall)
  }

  @Test
  fun testShouldTransformSimpleTupleIntoClosureCall() {
    assertEquals("name(result.0)", transformSimpleFunction().implicitClosureCall)
  }

  @Test
  fun testShouldTransform2ArgumentTupleIntoClosureCall() {
    assertEquals("name(result.0, result.1)", transform2ArgumentFunction().implicitClosureCall)
  }

  @Test
  fun testShouldSuppressWarningFromClosureCall() {
    assertEquals("_ = name()", transformReturnFunction().implicitClosureCall)
  }

  @Test
  fun testShouldTryToCallThrowingClosure() {
    assertEquals("try? name()", transformThrowingFunction().implicitClosureCall)
  }

  @Test
  fun testShouldTryToCallOptionalClosure() {
    assertEquals("name?()", transformOptionalFunction().implicitClosureCall)
  }

  private fun transformOptionalFunction(): ClosureParameterViewModel {
    val function = OptionalType.Builder()
        .type().function {}
        .build()
    return transform(function)!!
  }

  private fun transformReturnFunction(): ClosureParameterViewModel {
    val function = FunctionType.Builder()
        .returnType("T")
        .build()
    return transform(function)!!
  }

  private fun transformThrowingFunction(): ClosureParameterViewModel {
    val function = FunctionType.Builder()
        .throws()
        .build()
    return transform(function)!!
  }

  private fun transformSimpleFunction(): ClosureParameterViewModel {
    val function = FunctionType.Builder()
        .argument("A")
        .build()
    return transform(function)!!
  }

  private fun transform2ArgumentFunction(): ClosureParameterViewModel {
    val function = FunctionType.Builder()
        .argument("A")
        .argument("B")
        .build()
    return transform(function)!!
  }

  private fun transformEmptyFunction(): ClosureParameterViewModel {
    val function = FunctionType.Builder().build()
    return transform(function)!!
  }

  private fun transform(type: Type): ClosureParameterViewModel? {
    val visitor = FunctionParameterTransformer("name")
    type.accept(visitor)
    return visitor.transformed
  }
}
