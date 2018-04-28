package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ProtocolBuilderTest: TestCase() {

  fun testShouldBuildEmptyProtocol() {
    val protocol = Protocol.Builder().build()
    assertTrue(protocol.initializers.isEmpty())
    assertTrue(protocol.properties.isEmpty())
    assertTrue(protocol.methods.isEmpty())
    assertTrue(protocol.protocols.isEmpty())
  }

  fun testShouldBuildProtocolWithMethods() {
    val protocol = Protocol.Builder()
        .method("a") { }
        .method("b") { }
        .build()
    assertEquals("a", protocol.methods[0].name)
    assertEquals("b", protocol.methods[1].name)
  }

  fun testShouldBuildProtocolWithProperties() {
    val protocol = Protocol.Builder()
        .property("a") { }
        .property("b") { }
        .build()
    assertEquals("a", protocol.properties[0].name)
    assertEquals("b", protocol.properties[1].name)
  }

  fun testShouldBuildProtocolWithInitializers() {
    val protocol = Protocol.Builder()
        .initializer { }
        .initializer { }
        .build()
    assertEquals(2, protocol.initializers.size)
  }

  fun testShouldBuildClassWithInheritedProtocols() {
    val c = Protocol.Builder()
        .protocol { }
        .protocol { }
        .build()
    assertEquals(2, c.protocols.size)
  }
}
