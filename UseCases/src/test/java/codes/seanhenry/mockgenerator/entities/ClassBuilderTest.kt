package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ClassBuilderTest: TestCase() {

  fun testShouldBuildEmptyClass() {
    val c = Class.Builder().build()
    assertTrue(c.initializers.isEmpty())
    assertTrue(c.properties.isEmpty())
    assertTrue(c.methods.isEmpty())
    assertTrue(c.protocols.isEmpty())
    assertNull(c.superclass)
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
    assertNotNull(c.superclass)
  }

  fun testShouldBuildClassWithInheritedProtocols() {
    val c = Class.Builder()
        .protocol { }
        .protocol { }
        .build()
    assertEquals(2, c.protocols.size)
  }
}
