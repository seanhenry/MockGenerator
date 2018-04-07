package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*
import junit.framework.TestCase

class RecursiveVisitorTest: TestCase() {

  lateinit var visitor: RecursiveVisitorSpy

  override fun setUp() {
    super.setUp()
    visitor = RecursiveVisitorSpy()
  }

  fun testShouldVisitFunctionInnerTypes() {
    val function = FunctionType.Builder()
        .argument("A")
        .argument("B")
        .returnType("C")
        .build()
    function.accept(visitor)
    assertEquals(visitor.visitedFunctionTypes[0], function)
    assertEquals(visitor.visitedTypes[0], function.parameters[0])
    assertEquals(visitor.visitedTypes[1], function.parameters[1])
    assertEquals(visitor.visitedTypes[2], function.returnType)
  }

  fun testShouldVisitOptionalInnerType() {
    val optional = OptionalType.Builder().type("Type").build()
    optional.accept(visitor)
    assertEquals(visitor.visitedOptionalTypes[0], optional)
    assertEquals(visitor.visitedTypes[0], optional.type)
  }

  fun testShouldVisitBracketInnerType() {
    val optional = OptionalType.Builder().type().bracket().type("Type").build()
    val bracket = optional.type as BracketType
    optional.accept(visitor)
    assertEquals(visitor.visitedOptionalTypes[0], optional)
    assertEquals(visitor.visitedBracketTypes[0], bracket)
    assertEquals(visitor.visitedTypes[0], bracket.type)
  }

  fun testShouldVisitArrayInnerType() {
    val array = ArrayType.Builder().type("Type").build()
    array.accept(visitor)
    assertEquals(visitor.visitedArrayTypes[0], array)
    assertEquals(visitor.visitedTypes[0], array.type)
  }

  fun testShouldVisitDictionaryKeyAndValueTypes() {
    val dictionary = DictionaryType.Builder()
        .keyType("Key")
        .valueType("Value")
        .build()
    dictionary.accept(visitor)
    assertEquals(visitor.visitedDictionaryTypes[0], dictionary)
    assertEquals(visitor.visitedTypes[0], dictionary.keyType)
    assertEquals(visitor.visitedTypes[1], dictionary.valueType)
  }

  fun testShouldVisitGenericArgumentTypes() {
    val generic = GenericType.Builder("Type")
        .argument("T")
        .argument("U")
        .build()
    generic.accept(visitor)
    assertEquals(visitor.visitedGenericTypes[0], generic)
    assertEquals(visitor.visitedTypes[0], generic.arguments[0])
    assertEquals(visitor.visitedTypes[1], generic.arguments[1])
  }
}
