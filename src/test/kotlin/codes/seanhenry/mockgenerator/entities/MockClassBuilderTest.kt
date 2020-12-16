package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MockClassBuilderTest  {

  @Test
  fun testShouldBuildEmptyClass() {
    val c = MockClass.Builder().build()
    assertTrue(c.initializers.isEmpty())
    assertTrue(c.properties.isEmpty())
    assertTrue(c.methods.isEmpty())
    assertTrue(c.protocols.isEmpty())
    assertNull(c.inheritedClass)
    assertNull(c.scope)
  }

  @Test
  fun testShouldBuildClassWithInheritedClass() {
    val c = MockClass.Builder()
        .superclass { }
        .build()
    assertNotNull(c.inheritedClass)
  }

  @Test
  fun testShouldBuildClassWithInheritedProtocols() {
    val c = MockClass.Builder()
        .protocol { }
        .protocol { }
        .build()
    assertEquals(2, c.protocols.size)
  }

  @Test
  fun testShouldSetScope() {
    val c = MockClass.Builder()
        .scope("public")
        .build()
    assertEquals("public", c.scope)
  }
}
