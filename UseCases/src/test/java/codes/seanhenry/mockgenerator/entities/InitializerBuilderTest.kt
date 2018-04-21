package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class InitializerBuilderTest: TestCase() {

  fun testShouldBuildEmptyInitialiser() {
    val initializer = Initializer.Builder().build()
    assertTrue(initializer.parametersList.isEmpty())
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
    assertFalse(initializer.isProtocol)
  }

  fun testShouldBuildInitialiserWithParameters() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("Type") }
        .build()
    assertEquals("a: Type", initializer.parametersList[0].text)
    assertEquals("a", initializer.parametersList[0].internalName)
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
    assertFalse(initializer.isProtocol)
  }

  fun testShouldBuildInitialiserWithLabelledParameters() {
    val initializer = Initializer.Builder()
        .parameter("a", "b") { it.type("Type") }
        .build()
    assertEquals("a b: Type", initializer.parametersList[0].text)
    assertEquals("a", initializer.parametersList[0].externalName)
    assertEquals("b", initializer.parametersList[0].internalName)
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
    assertFalse(initializer.isProtocol)
  }

  fun testShouldBuildFailableInitialiser() {
    val initializer = Initializer.Builder()
        .failable()
        .build()
    assertTrue(initializer.isFailable)
  }

  fun testShouldBuildThrowingInitialiser() {
    val initializer = Initializer.Builder()
        .throws()
        .build()
    assertTrue(initializer.throws)
  }

  fun testShouldBuildIsProtocolInitialiser() {
    val initializer = Initializer.Builder()
        .protocol()
        .build()
    assertTrue(initializer.isProtocol)
  }
}
