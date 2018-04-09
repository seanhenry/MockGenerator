package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ProtocolBuilderTest: TestCase() {

  fun testShouldBuildEmptyProtocol() {
    val protocol = Protocol.Builder().build()
    assertTrue(protocol.methods.isEmpty())
  }

  fun testShouldBuildProtocolWithMethods() {
    val protocol = Protocol.Builder()
        .method("a") { }
        .method("b") { }
        .build()
    assertEquals("a", protocol.methods[0].name)
    assertEquals("b", protocol.methods[1].name)
  }
}
