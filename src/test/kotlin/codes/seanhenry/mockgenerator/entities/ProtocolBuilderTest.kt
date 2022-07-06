package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProtocolBuilderTest {

  @Test
  fun testShouldBuildEmptyProtocol() {
    val protocol = Protocol.Builder().build()
    assertTrue(protocol.initializers.isEmpty())
    assertTrue(protocol.properties.isEmpty())
    assertTrue(protocol.methods.isEmpty())
    assertTrue(protocol.protocols.isEmpty())
  }

  @Test
  fun testShouldBuildProtocolWithMethods() {
    val protocol = Protocol.Builder()
        .method("a") { }
        .method("b") { }
        .build()
    assertEquals("a", protocol.methods[0].name)
    assertEquals("b", protocol.methods[1].name)
  }

  @Test
  fun testShouldBuildProtocolWithProperties() {
    val protocol = Protocol.Builder()
        .property("a") { }
        .property("b") { }
        .build()
    assertEquals("a", protocol.properties[0].name)
    assertEquals("b", protocol.properties[1].name)
  }

  @Test
  fun testShouldBuildProtocolWithSubscripts() {
    val protocol = Protocol.Builder()
        .subscript(TypeIdentifier("Int")) { }
        .subscript(TypeIdentifier("String")) { }
        .build()
    assertEquals("subscript() -> Int", protocol.subscripts[0].declarationText)
    assertEquals("subscript() -> String", protocol.subscripts[1].declarationText)
  }

  @Test
  fun testShouldBuildProtocolWithInitializers() {
    val protocol = Protocol.Builder()
        .initializer { }
        .initializer { }
        .build()
    assertEquals(2, protocol.initializers.size)
  }

  @Test
  fun testShouldBuildClassWithInheritedProtocols() {
    val c = Protocol.Builder()
        .protocol { }
        .protocol { }
        .build()
    assertEquals(2, c.protocols.size)
  }
}
