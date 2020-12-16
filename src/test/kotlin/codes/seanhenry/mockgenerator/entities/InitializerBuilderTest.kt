package codes.seanhenry.mockgenerator.entities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InitializerBuilderTest {

  @Test
  fun testShouldBuildEmptyInitialiser() {
    val initializer = Initializer.Builder().build()
    assertTrue(initializer.parametersList.isEmpty())
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
  }

  @Test
  fun testShouldBuildInitialiserWithParameters() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("Type") }
        .build()
    assertEquals("a: Type", initializer.parametersList[0].text)
    assertEquals("a", initializer.parametersList[0].internalName)
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
  }

  @Test
  fun testShouldBuildInitialiserWithLabelledParameters() {
    val initializer = Initializer.Builder()
        .parameter("a", "b") { it.type("Type") }
        .build()
    assertEquals("a b: Type", initializer.parametersList[0].text)
    assertEquals("a", initializer.parametersList[0].externalName)
    assertEquals("b", initializer.parametersList[0].internalName)
    assertFalse(initializer.isFailable)
    assertFalse(initializer.throws)
  }

  @Test
  fun testShouldBuildFailableInitialiser() {
    val initializer = Initializer.Builder()
        .failable()
        .build()
    assertTrue(initializer.isFailable)
  }

  @Test
  fun testShouldBuildThrowingInitialiser() {
    val initializer = Initializer.Builder()
        .throws()
        .build()
    assertTrue(initializer.throws)
  }
}
