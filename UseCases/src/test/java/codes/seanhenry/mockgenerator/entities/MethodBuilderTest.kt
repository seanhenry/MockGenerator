package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class MethodBuilderTest: TestCase() {

  lateinit var method: Method

  fun testShouldBuildDefaultMethod() {
    method = Method.Builder("name").build()
    assertEquals("name", method.name)
    assertEquals(ResolvedType.IMPLICIT, method.returnType)
    assertTrue(method.parametersList.isEmpty())
    assertFalse(method.throws)
    assertEquals("func name()", method.declarationText)
  }

  fun testReturnType() {
    method = Method.Builder("name").returnType("Type").build()
    assertEquals("Type", method.returnType.originalType.text)
    assertEquals("Type", method.returnType.resolvedType.text)
    assertEquals("func name() -> Type", method.declarationText)
  }

  fun testThrows() {
    method = Method.Builder("name").throws().build()
    assertTrue(method.throws)
    assertEquals("func name() throws", method.declarationText)
  }

  fun testThrowsAndReturns() {
    method = Method.Builder("name").throws().returnType("Type").build()
    assertEquals("func name() throws -> Type", method.declarationText)
  }

  fun testRethrows() {
    method = Method.Builder("name").rethrows().build()
    assertTrue(method.rethrows)
    assertEquals("func name() rethrows", method.declarationText)
  }

  fun testRethrowsAndReturns() {
    method = Method.Builder("name").rethrows().returnType("Type").build()
    assertEquals("func name() rethrows -> Type", method.declarationText)
  }

  fun testParameter() {
    method = Method.Builder("a").parameter("name") { it.type("Type") }.build()
    assertEquals("name", getParameter(0).internalName)
    assertEquals("Type", getParameter(0).type.originalType.text)
    assertEquals("func a(name: Type)", method.declarationText)
  }

  fun testParameters() {
    method = Method.Builder("a")
        .parameter("name") { it.type("Type") }
        .parameter("name2") { it.type("Type2") }
        .build()
    assertEquals("name", getParameter(0).internalName)
    assertEquals("Type", getParameter(0).type.originalType.text)
    assertEquals("name2", getParameter(1).internalName)
    assertEquals("Type2", getParameter(1).type.originalType.text)
    assertEquals("func a(name: Type, name2: Type2)", method.declarationText)
  }

  fun testGenericParameter() {
    method = Method.Builder("a")
        .genericParameter("T")
        .build()
    assertEquals("T", method.genericParameters[0])
    assertEquals("func a<T>()", method.declarationText)
  }

  private fun getParameter(index: Int): Parameter {
    return method.parametersList[index]
  }
}
