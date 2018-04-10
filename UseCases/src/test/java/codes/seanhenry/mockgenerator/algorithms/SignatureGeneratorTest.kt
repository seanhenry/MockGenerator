package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Element
import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import junit.framework.TestCase

class SignatureGeneratorTest : TestCase() {

  fun testShouldGenerateEmptyMethodSignature() {
    val method = Method.Builder("method").build()
    assertEquals("method()", getSignature(method))
  }

  fun testShouldGenerateSimpleParameterMethod() {
    val method = Method.Builder("method")
        .parameter("a") { it.type("A") }
        .build()
    assertEquals("method(a:A)", getSignature(method))
  }

  fun testShouldChooseParameterExternalNameAndIgnoreInternalName() {
    val method = Method.Builder("method")
        .parameter("ext", "int") { it.type("A") }
        .build()
    assertEquals("method(ext:A)", getSignature(method))
  }

  fun testShouldGenerateMultipleParameters() {
    val method = Method.Builder("method")
        .parameter("a") { it.type("A") }
        .parameter("b") { it.type("B") }
        .build()
    assertEquals("method(a:A,b:B)", getSignature(method))
  }

  fun testShouldGenerateReturnType() {
    val method = Method.Builder("method")
        .returnType("A")
        .build()
    assertEquals("method():A", getSignature(method))
  }

  fun testShouldGenerateInitializer() {
    val method = Initializer.Builder()
        .parameter("_", "a") { it.type("A") }
        .parameter("b") { it.type("B") }
        .build()
    assertEquals("init(_:A,b:B)", getSignature(method))
  }

  fun testShouldGenerateSignatureForPropertyJustByItsName() {
    val method = Property.Builder("property")
        .type("Int")
        .build()
    assertEquals("property", getSignature(method))
  }

  private fun getSignature(element: Element): String {
    return SignatureGenerator.signature(element)
  }
}
