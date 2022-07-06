package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignatureGeneratorTest  {

  @Test
  fun testShouldGenerateEmptyMethodSignature() {
    val method = Method.Builder("method").build()
    assertEquals("method()", getSignature(method))
  }

  @Test
  fun testShouldGenerateSimpleParameterMethod() {
    val method = Method.Builder("method")
        .parameter("a") { it.type("A") }
        .build()
    assertEquals("method(a:A)", getSignature(method))
  }

  @Test
  fun testShouldChooseParameterExternalNameAndIgnoreInternalName() {
    val method = Method.Builder("method")
        .parameter("ext", "int") { it.type("A") }
        .build()
    assertEquals("method(ext:A)", getSignature(method))
  }

  @Test
  fun testShouldGenerateMultipleParameters() {
    val method = Method.Builder("method")
        .parameter("a") { it.type("A") }
        .parameter("b") { it.type("B") }
        .build()
    assertEquals("method(a:A,b:B)", getSignature(method))
  }

  @Test
  fun testShouldGenerateReturnType() {
    val method = Method.Builder("method")
        .returnType("A")
        .build()
    assertEquals("method():A", getSignature(method))
  }

  @Test
  fun testShouldGenerateInitializer() {
    val method = Initializer.Builder()
        .parameter("_", "a") { it.type("A") }
        .parameter("b") { it.type("B") }
        .build()
    assertEquals("init(_:A,b:B)", getSignature(method))
  }

  @Test
  fun testShouldGenerateSignatureForPropertyJustByItsName() {
    val method = Property.Builder("property")
        .type("Int")
        .build()
    assertEquals("property", getSignature(method))
  }

  fun testShouldGenerateSubscript() {
    val subscript = Subscript.Builder(TypeIdentifier("Int"))
        .build()
    assertEquals("subscript():Int", getSignature(subscript))
  }

  fun testShouldGenerateSubscriptFromResolvedType() {
    val subscript = Subscript.Builder(ResolvedType(TypeIdentifier("A"), TypeIdentifier("B")))
        .build()
    assertEquals("subscript():B", getSignature(subscript))
  }

  fun testShouldGenerateSubscriptWithParameters() {
    val subscript = Subscript.Builder(TypeIdentifier("Int"))
        .parameter("a") { it.type("String") }
        .parameter("b", "c") { it.type("UInt") }
        .build()
    assertEquals("subscript(a:String,b:UInt):Int", getSignature(subscript))
  }

  private fun getSignature(element: Element): String {
    return SignatureGenerator.signature(element)
  }
}
