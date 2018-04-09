package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*
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

  fun testShouldVisitMethod() {
    val declaration = Method.Builder("a").build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitMethod)
  }

  fun testShouldVisitProperty() {
    val declaration = Property.Builder("a").build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitProperty)
  }

  fun testShouldVisitInitializer() {
    val declaration = Initializer.Builder().build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitInitializer)
  }

  fun testShouldVisitParameter() {
    val parameter = Parameter.Builder("_").build()
    parameter.accept(visitor)
    assertTrue(visitor.didVisitParameter)
  }
}
