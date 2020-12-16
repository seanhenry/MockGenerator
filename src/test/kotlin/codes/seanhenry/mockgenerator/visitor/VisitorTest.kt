package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class VisitorTest {

  lateinit var visitor: VisitorSpy

  @BeforeEach
  fun setUp() {
    visitor = VisitorSpy()
  }

  @Test
  fun testShouldVisitTypeIdentifier() {
    val type = TypeIdentifier("Type")
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitTypeIdentifier)
  }

  @Test
  fun testShouldVisitFunctionType() {
    val type = FunctionType.Builder().build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitFunctionType)
  }

  @Test
  fun testShouldVisitOptionalType() {
    val type = OptionalType.Builder().type("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitOptionalType)
  }

  @Test
  fun testShouldVisitTupleType() {
    val type = TupleType.Builder().element("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitTupleType)
  }

  @Test
  fun testShouldVisitArrayType() {
    val type = ArrayType.Builder().type("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitArrayType)
  }

  @Test
  fun testShouldVisitDictionaryType() {
    val type = DictionaryType.Builder()
        .keyType("Key")
        .valueType("Value")
        .build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitDictionaryType)
  }

  @Test
  fun testShouldVisitGenericType() {
    val type = GenericType.Builder("Type").build()
    type.accept(visitor)
    assertTrue(visitor.didVisitType)
    assertTrue(visitor.didVisitGenericType)
  }

  @Test
  fun testShouldVisitMethod() {
    val declaration = Method.Builder("a").build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitMethod)
  }

  @Test
  fun testShouldVisitProperty() {
    val declaration = Property.Builder("a").build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitProperty)
  }

  @Test
  fun testShouldVisitInitializer() {
    val declaration = Initializer.Builder().build()
    declaration.accept(visitor)
    assertTrue(visitor.didVisitInitializer)
  }

  @Test
  fun testShouldVisitParameter() {
    val parameter = Parameter.Builder("_").build()
    parameter.accept(visitor)
    assertTrue(visitor.didVisitParameter)
  }
}
