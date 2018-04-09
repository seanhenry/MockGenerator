package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class InitializerBuilderTest: TestCase() {

  fun testShouldBuildEmptyInitialiser() {
    val initialiser = Initializer.Builder().build()
    assertTrue(initialiser.parametersList.isEmpty())
    assertFalse(initialiser.isFailable)
    assertFalse(initialiser.throws)
    assertFalse(initialiser.isProtocol)
  }

  fun testShouldBuildInitialiserWithParameters() {
    val initialiser = Initializer.Builder()
        .parameter("a") { it.type("Type") }
        .build()
    assertEquals("a: Type", initialiser.parametersList[0].text)
    assertEquals("a", initialiser.parametersList[0].name)
    assertFalse(initialiser.isFailable)
    assertFalse(initialiser.throws)
    assertFalse(initialiser.isProtocol)
  }

  fun testShouldBuildInitialiserWithLabelledParameters() {
    val initialiser = Initializer.Builder()
        .parameter("a", "b") { it.type("Type") }
        .build()
    assertEquals("a b: Type", initialiser.parametersList[0].text)
    assertEquals("a", initialiser.parametersList[0].label)
    assertEquals("b", initialiser.parametersList[0].name)
    assertFalse(initialiser.isFailable)
    assertFalse(initialiser.throws)
    assertFalse(initialiser.isProtocol)
  }

  fun testShouldBuildFailableInitialiser() {
    val initialiser = Initializer.Builder()
        .failable()
        .build()
    assertTrue(initialiser.isFailable)
  }

  fun testShouldBuildThrowingInitialiser() {
    val initialiser = Initializer.Builder()
        .throws()
        .build()
    assertTrue(initialiser.throws)
  }

  fun testShouldBuildIsProtocolInitialiser() {
    val initialiser = Initializer.Builder()
        .protocol()
        .build()
    assertTrue(initialiser.isProtocol)
  }
}
