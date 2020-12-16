package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initializer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CreateConvenienceInitializerTest {

  @Test
  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initializer = Initializer.Builder().build()
    assertNull(CreateConvenienceInitialiser().transform(initializer))
  }

  @Test
  fun testShouldReturnNilForThrowingInitialiserWithNoArguments() {
    val initializer = Initializer.Builder()
        .throws()
        .build()
    assertNull(CreateConvenienceInitialiser().transform(initializer))
  }

  @Test
  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("String") }
        .build()
    assertEquals(initializer.parametersList, CreateConvenienceInitialiser().transform(initializer)?.parameters)
  }

  @Test
  fun testShouldReturnFailableInitialiserWhenFailable() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("A") }
        .failable()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.isFailable)
  }

  @Test
  fun testShouldReturnThrowingInitialiserWhenThrows() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("String") }
        .throws()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.throws)
  }

  @Test
  fun testShouldReturnFailableInitialiserWhenFailableAndNoArguments() {
    val initializer = Initializer.Builder()
        .failable()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.isFailable)
    assertEquals(initializer.parametersList, CreateConvenienceInitialiser().transform(initializer)?.parameters)
  }
}
