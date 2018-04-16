package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Type
import junit.framework.TestCase

class TypeErasingVisitorTest: TestCase() {

  lateinit var method: Method

  fun testShouldEraseNestedType() {
    method = buildMethod()
        .parameter("t") { it.type().typeIdentifier("T") { it.nest("Nested") } }
        .build()
    assertEquals("Any", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseType() {
    method = buildMethod()
        .parameter("t") { it.type("T") }
        .build()
    assertEquals("Any", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseOptional() {
    method = buildMethod()
        .parameter("t") { it.type().optional { it.type("T") } }
        .build()
    assertEquals("Any?", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseFunction() {
    method = buildMethod()
        .parameter("t") { it.type().function { func ->
          func.argument("T").returnType("T")
        } }
        .build()
    assertEquals("(Any) -> Any", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseTypesWithMultipleGenerics() {
    method = buildMethod()
        .parameter("t") { it.type().function { func ->
          func.argument("T").returnType("U")
        } }
        .build()
    assertEquals("(Any) -> Any", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseTuple() {
    method = buildMethod()
        .parameter("t") { it.type().tuple { tuple ->
          tuple.element("T")
              .element("Int")
        } }
        .build()
    assertEquals("(Any, Int)", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldEraseDictionaryKeyToAnyHashable() {
    method = buildMethod()
        .parameter("t") { it.type().dictionary { dict ->
          dict.keyType("T")
              .valueType("U")
        } }
        .build()
    assertEquals("[AnyHashable: Any]", erase(method.parametersList[0].type.originalType).text)
  }

  fun testShouldNotEraseDictionaryKeyWhenNotGeneric() {
    method = buildMethod()
        .parameter("t") { it.type().dictionary { dict ->
          dict.keyType("Key")
              .valueType("Value")
        } }
        .build()
    assertEquals("[Key: Value]", erase(method.parametersList[0].type.originalType).text)
  }

  private fun buildMethod(): Method.Builder {
    return Method.Builder("method")
  }

  private fun erase(type: Type): Type {
    TypeErasingVisitor.erase(type, listOf("T", "U"))
    return type
  }
}
