package codes.seanhenry.mockgenerator.transformer

import codes.seanhenry.mockgenerator.ast.FunctionType
import codes.seanhenry.mockgenerator.ast.OptionalType
import codes.seanhenry.mockgenerator.ast.Type
import codes.seanhenry.mockgenerator.ast.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.ClosureParameterViewModel
import junit.framework.TestCase

class FunctionParameterTransformerTest: TestCase() {

  fun testShouldCapitalizeName() {
    assertEquals("Name", transformEmptyFunction().capitalizedName)
  }

  fun testShouldPreserveName() {
    assertEquals("name", transformEmptyFunction().name)
  }

  fun testShouldBeFalseWhenNoArguments() {
    assertFalse(transformEmptyFunction().hasArguments)
  }

  fun testShouldBeTrueNoArguments() {
    assertTrue(transformSimpleFunction().hasArguments)
  }

  fun testShouldTransformEmptyFunctionToEmptyTuple() {
    assertEquals("()", transformEmptyFunction().argumentsTupleRepresentation)
  }

  fun testShouldTransformAppendVoidTo1ArgumentFunctionTuple() {
    assertEquals("(A, Void)", transformSimpleFunction().argumentsTupleRepresentation)
  }

  fun testShouldTransformCreateTupleFrom2ArgumentFunctionTuple() {
    assertEquals("(A, B)", transform2ArgumentFunction().argumentsTupleRepresentation)
  }

  fun testShouldTransformEmptyTupleIntoClosureCall() {
    assertEquals("name()", transformEmptyFunction().implicitClosureCall)
  }

  fun testShouldTransformSimpleTupleIntoClosureCall() {
    assertEquals("name(result.0)", transformSimpleFunction().implicitClosureCall)
  }

  fun testShouldTransform2ArgumentTupleIntoClosureCall() {
    assertEquals("name(result.0, result.1)", transform2ArgumentFunction().implicitClosureCall)
  }

  fun testShouldSuppressWarningFromClosureCall() {
    assertEquals("_ = name()", transformReturnFunction().implicitClosureCall)
  }

  fun testShouldTryToCallThrowingClosure() {
    assertEquals("try? name()", transformThrowingFunction().implicitClosureCall)
  }

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
