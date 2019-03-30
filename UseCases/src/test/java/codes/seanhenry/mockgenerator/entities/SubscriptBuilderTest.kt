package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class SubscriptBuilderTest : TestCase() {
  fun testShouldBuildSubscriptWithReturnType() {
    val subscript = Subscript.Builder(TypeIdentifier.INT).build()
    assertEquals(TypeIdentifier.INT, subscript.returnType.originalType)
    assertEquals(TypeIdentifier.INT, subscript.returnType.resolvedType)
    assertEquals("subscript() -> Int", subscript.declarationText)
  }

  fun testShouldBuildSubscriptWithParameter() {
    val subscript = Subscript.Builder(TypeIdentifier.INT)
        .parameter("a") { it.type().type("String") }
        .build()
    assertEquals("a: String", subscript.parameters[0].text)
    assertEquals("subscript(a: String) -> Int", subscript.declarationText)
  }

  fun testShouldBuildSubscriptWithParameters() {
    val subscript = Subscript.Builder(TypeIdentifier.INT)
        .parameter("a") { it.type().type("String") }
        .parameter("b", "b") { it.type().type("UInt") }
        .build()
    assertEquals("a: String", subscript.parameters[0].text)
    assertEquals("b b: UInt", subscript.parameters[1].text)
    assertEquals("subscript(a: String, b b: UInt) -> Int", subscript.declarationText)
  }
}