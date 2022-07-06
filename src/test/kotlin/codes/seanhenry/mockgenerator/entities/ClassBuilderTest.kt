package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ClassBuilderTest {

  @Test
  fun testShouldBuildEmptyClass() {
    val c = Class.Builder().build()
    assertTrue(c.initializers.isEmpty())
    assertTrue(c.properties.isEmpty())
    assertTrue(c.methods.isEmpty())
    assertTrue(c.protocols.isEmpty())
    assertNull(c.inheritedClass)
  }

  @Test
  fun testShouldBuildClassWithMethods() {
    val c = Class.Builder()
        .method("a") { }
        .method("b") { }
        .build()
    assertEquals("a", c.methods[0].name)
    assertEquals("b", c.methods[1].name)
  }

  @Test
  fun testShouldBuildClassWithProperties() {
    val c = Class.Builder()
        .property("a") { }
        .property("b") { }
        .build()
    assertEquals("a", c.properties[0].name)
    assertEquals("b", c.properties[1].name)
  }

  @Test
  fun testShouldBuildClassWithSubscripts() {
    val c = Class.Builder()
        .subscript(TypeIdentifier("Int")) { }
        .subscript(TypeIdentifier("String")) { }
        .build()
    assertEquals("subscript() -> Int", c.subscripts[0].declarationText)
    assertEquals("subscript() -> String", c.subscripts[1].declarationText)
  }

  @Test
  fun testShouldBuildClassWithInitializers() {
    val c = Class.Builder()
        .initializer { }
        .initializer { }
        .build()
    assertEquals(2, c.initializers.size)
  }

  @Test
  fun testShouldBuildClassWithInheritedClass() {
    val c = Class.Builder()
        .superclass { }
        .build()
    assertNotNull(c.inheritedClass)
  }
}
