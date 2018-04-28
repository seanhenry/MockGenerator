package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class MockClassBuilderTest : TestCase() {

  fun testShouldBuildEmptyClass() {
    val c = MockClass.Builder().build()
    assertTrue(c.initializers.isEmpty())
    assertTrue(c.properties.isEmpty())
    assertTrue(c.methods.isEmpty())
    assertTrue(c.protocols.isEmpty())
    assertNull(c.superclass)
    assertNull(c.scope)
  }

  fun testShouldBuildClassWithInheritedClass() {
    val c = MockClass.Builder()
        .superclass { }
        .build()
    assertNotNull(c.superclass)
  }

  fun testShouldBuildClassWithInheritedProtocols() {
    val c = MockClass.Builder()
        .protocol { }
        .protocol { }
        .build()
    assertEquals(2, c.protocols.size)
  }

  fun testShouldSetScope() {
    val c = MockClass.Builder()
        .scope("public")
        .build()
    assertEquals("public", c.scope)
  }
}
