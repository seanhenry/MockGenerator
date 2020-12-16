package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ParameterBuilderTest {

  @Test
  fun testName() {
    val param = Parameter.Builder("name").build()
    assertEquals("name", param.internalName)
    assertNull(param.externalName)
    assertEquals(ResolvedType.IMPLICIT, param.type)
    assertEquals("name: ", param.text)
  }

  @Test
  fun testInternalExternalLabels() {
    val param = Parameter.Builder("external", "internal").build()
    assertEquals("internal", param.internalName)
    assertEquals("external", param.externalName)
    assertEquals(ResolvedType.IMPLICIT, param.type)
    assertEquals("external internal: ", param.text)
  }

  @Test
  fun testType() {
    val param = Parameter.Builder("name").type("Type").build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("name: Type", param.text)
  }

  @Test
  fun testEscaping() {
    val param = Parameter.Builder("name").type("Type").escaping().build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertTrue(param.isEscaping)
    assertEquals("name: @escaping Type", param.text)
  }

  @Test
  fun testInout() {
    val param = Parameter.Builder("name").type("Type").inout().build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("name: inout Type", param.text)
  }

  @Test
  fun testAnnotation() {
    val param = Parameter.Builder("name").type("Type").annotation("@objc").build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("Type", param.type.resolvedType.text)
    assertEquals("name: @objc Type", param.text)
  }

  @Test
  fun testResolvedType() {
    val param = Parameter.Builder("name").type("Type").resolvedType().function { }.build()
    assertEquals("Type", param.type.originalType.text)
    assertEquals("() -> ()", param.type.resolvedType.text)
    assertEquals("name: Type", param.text)
  }
}
