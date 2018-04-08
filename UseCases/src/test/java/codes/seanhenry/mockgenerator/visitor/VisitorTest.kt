package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*
import junit.framework.TestCase

class VisitorTest: TestCase() {

  lateinit var visitor: VisitorSpy

  override fun setUp() {
    super.setUp()
    visitor = VisitorSpy()
  }

  fun testShouldVisitTypeIdentifier() {
    val type = TypeIdentifier("Type")
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitTypeIdentifier)
  }

  fun testShouldVisitFunctionType() {
    val type = FunctionType.Builder().build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitFunctionType)
  }

  fun testShouldVisitOptionalType() {
    val type = OptionalType.Builder().type("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitOptionalType)
  }

  fun testShouldVisitBracketType() {
    val type = BracketType(TypeIdentifier("Type"))
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitBracketType)
  }

  fun testShouldVisitArrayType() {
    val type = ArrayType.Builder().type("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitArrayType)
  }

  fun testShouldVisitDictionaryType() {
    val type = DictionaryType.Builder()
        .keyType("Key")
        .valueType("Value")
        .build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitDictionaryType)
  }

  fun testShouldVisitGenericType() {
    val type = GenericType.Builder("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitGenericType)
  }
}
