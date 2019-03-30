package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ClassBuilderTest: TestCase() {

  fun testShouldBuildEmptyClass() {
    val c = Class.Builder().build()
    assertTrue(c.initializers.isEmpty())
    assertTrue(c.properties.isEmpty())
    assertTrue(c.methods.isEmpty())
    assertTrue(c.protocols.isEmpty())
    assertNull(c.inheritedClass)
  }

  fun testShouldBuildClassWithMethods() {
    val c = Class.Builder()
        .method("a") { }
        .method("b") { }
        .build()
    assertEquals("a", c.methods[0].name)
    assertEquals("b", c.methods[1].name)
  }

  fun testShouldBuildClassWithProperties() {
    val c = Class.Builder()
        .property("a") { }
        .property("b") { }
        .build()
    assertEquals("a", c.properties[0].name)
    assertEquals("b", c.properties[1].name)
  }

  fun testShouldBuildClassWithSubscripts() {
    val c = Class.Builder()
        .subscript(TypeIdentifier("Int")) { }
        .subscript(TypeIdentifier("String")) { }
        .build()
    assertEquals("subscript() -> Int", c.subscripts[0].declarationText)
    assertEquals("subscript() -> String", c.subscripts[1].declarationText)
  }

  fun testShouldBuildClassWithInitializers() {
    val c = Class.Builder()
        .initializer { }
        .initializer { }
        .build()
    assertEquals(2, c.initializers.size)
  }

  fun testShouldBuildClassWithInheritedClass() {
    val c = Class.Builder()
        .superclass { }
        .build()
    assertNotNull(c.inheritedClass)
  }
}
