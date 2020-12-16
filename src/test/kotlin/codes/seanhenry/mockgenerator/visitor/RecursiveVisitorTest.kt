package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RecursiveVisitorTest {

  lateinit var visitor: RecursiveVisitorSpy

  @BeforeEach
  fun setUp() {
    visitor = RecursiveVisitorSpy()
  }

  @Test
  fun testShouldVisitFunctionInnerTypes() {
    val function = FunctionType.Builder()
        .argument("A")
        .argument("B")
        .returnType("C")
        .build()
    function.accept(visitor)
    assertEquals(visitor.visitedFunctionTypes[0], function)
    assertEquals(visitor.visitedTypes[0], function.arguments[0])
    assertEquals(visitor.visitedTypes[1], function.arguments[1])
    assertEquals(visitor.visitedTypes[2], function.returnType)
  }

  @Test
  fun testShouldVisitOptionalInnerType() {
    val optional = OptionalType.Builder().type("Type").build()
    optional.accept(visitor)
    assertEquals(visitor.visitedOptionalTypes[0], optional)
    assertEquals(visitor.visitedTypes[0], optional.type)
  }

  @Test
  fun testShouldVisitTupleInnerType() {
    val optional = OptionalType.Builder().type().bracket().type("Type").build()
    val tuple = optional.type as TupleType
    optional.accept(visitor)
    assertEquals(visitor.visitedOptionalTypes[0], optional)
    assertEquals(visitor.visitedTupleTypes[0], tuple)
    assertEquals(visitor.visitedTypes[0], tuple.types[0])
  }

  @Test
  fun testShouldVisitArrayInnerType() {
    val array = ArrayType.Builder().type("Type").build()
    array.accept(visitor)
    assertEquals(visitor.visitedArrayTypes[0], array)
    assertEquals(visitor.visitedTypes[0], array.type)
  }

  @Test
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

  @Test
  fun testShouldVisitGenericTypes() {
    val generic = GenericType.Builder("Type")
        .argument("T")
        .argument("U")
        .build()
    generic.accept(visitor)
    assertEquals(visitor.visitedGenericTypes[0], generic)
    assertEquals(visitor.visitedTypes[0], generic.arguments[0])
    assertEquals(visitor.visitedTypes[1], generic.arguments[1])
  }

  @Test
  fun testShouldVisitMethodChildren() {
    val declaration = Method.Builder("method")
        .parameter("a") { it.type("Int") }
        .returnType("String")
        .build()
    declaration.accept(visitor)
    assertEquals(visitor.visitedMethods[0], declaration)
    assertEquals(visitor.visitedParameters[0], declaration.parametersList[0])
    assertEquals(visitor.visitedTypes[0], declaration.parametersList[0].type.resolvedType)
    assertEquals(visitor.visitedTypes[1], declaration.returnType.resolvedType)
  }

  @Test
  fun testShouldVisitPropertyChildren() {
    val declaration = Property.Builder("prop")
        .type("String")
        .build()
    declaration.accept(visitor)
    assertEquals(visitor.visitedProperties[0], declaration)
    assertEquals(visitor.visitedTypes[0], declaration.type)
  }

  @Test
  fun testShouldVisitInitializerChildren() {
    val declaration = Initializer.Builder()
        .parameter("a") { it.type("Int") }
        .build()
    declaration.accept(visitor)
    assertEquals(visitor.visitedInitializers[0], declaration)
    assertEquals(visitor.visitedParameters[0], declaration.parametersList[0])
  }

  @Test
  fun testShouldVisitParameterChildren() {
    val parameter = Parameter.Builder("a")
        .type("Int")
        .build()
    parameter.accept(visitor)
    assertEquals(visitor.visitedParameters[0], parameter)
    assertEquals(visitor.visitedTypes[0], parameter.type.resolvedType)
  }

  @Test
  fun testShouldVisitTupleTypes() {
    val tuple = TupleType.Builder()
        .element("A")
        .element("B")
        .build()
    tuple.accept(visitor)
    assertEquals(tuple.types[0], visitor.visitedTypes[0])
    assertEquals(tuple.types[1], visitor.visitedTypes[1])
  }
}
