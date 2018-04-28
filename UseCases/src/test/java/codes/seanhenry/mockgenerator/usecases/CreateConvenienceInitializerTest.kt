package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initializer
import junit.framework.TestCase

class CreateConvenienceInitializerTest: TestCase() {

  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initializer = Initializer.Builder().build()
    assertNull(CreateConvenienceInitialiser().transform(initializer))
  }

  fun testShouldReturnNilForThrowingInitialiserWithNoArguments() {
    val initializer = Initializer.Builder()
        .throws()
        .build()
    assertNull(CreateConvenienceInitialiser().transform(initializer))
  }

  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("String") }
        .build()
    assertEquals(initializer.parametersList, CreateConvenienceInitialiser().transform(initializer)?.parameters)
  }

  fun testShouldReturnFailableInitialiserWhenFailable() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("A") }
        .failable()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.isFailable)
  }

  fun testShouldReturnThrowingInitialiserWhenThrows() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type("String") }
        .throws()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.throws)
  }

  fun testShouldReturnFailableInitialiserWhenFailableAndNoArguments() {
    val initializer = Initializer.Builder()
        .failable()
        .build()
    assertEquals(true, CreateConvenienceInitialiser().transform(initializer)?.isFailable)
    assertEquals(initializer.parametersList, CreateConvenienceInitialiser().transform(initializer)?.parameters)
  }
}
