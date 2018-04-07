package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.Type
import junit.framework.TestCase

class ParameterBuilderTest: TestCase() {

  fun testName() {
    val param = Parameter.Builder("name").build()
    assertEquals("name", param.name)
    assertEquals("", param.label) // TODO: should be nil
    assertEquals(MethodType.IMPLICIT, param.type)
    assertEquals("name: ", param.text)
  }

  fun testInternalExternalLabels() {
    val param = Parameter.Builder("external", "internal").build()
    assertEquals("internal", param.name)
    assertEquals("external", param.label)
    assertEquals(MethodType.IMPLICIT, param.type)
    assertEquals("external internal: ", param.text)
  }

  fun testType() {
    val param = Parameter.Builder("name").type("Type").build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("Type", param.type.erasedType.text)
    assertEquals("name: Type", param.text)
  }

  fun testEscaping() {
    val param = Parameter.Builder("name").type("Type").escaping().build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("Type", param.type.erasedType.text)
    assertTrue(param.isEscaping)
    assertEquals("name: @escaping Type", param.text)
  }

  fun testInout() {
    val param = Parameter.Builder("name").type("Type").inout().build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("Type", param.type.erasedType.text)
    assertEquals("name: inout Type", param.text)
  }

  fun testAnootation() {
    val param = Parameter.Builder("name").type("Type").annotation("@objc").build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("Type", param.type.erasedType.text)
    assertEquals("name: @objc Type", param.text)
  }
}
