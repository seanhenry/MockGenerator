package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initializer
import junit.framework.TestCase

class CreateConvenienceInitializerTest: TestCase() {

  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initialiser = Initializer("", false)
    assertNull(CreateConvenienceInitialiser().transform(initialiser))
  }

  fun testShouldReturnNilForThrowingInitialiserWithNoArguments() {
    val initialiser = Initializer("", false, true)
    assertNull(CreateConvenienceInitialiser().transform(initialiser))
  }

  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initialiser = Initializer("a: String", false)
    assertEquals(initialiser.parametersList, CreateConvenienceInitialiser().transform(initialiser)?.parameters)
  }

  fun testShouldReturnFailableInitialiserWhenFailable() {
    val initialiser = Initializer("a: String", true)
    assertEquals(true, CreateConvenienceInitialiser().transform(initialiser)?.isFailable)
  }

  fun testShouldReturnThrowingInitialiserWhenThrows() {
    val initialiser = Initializer("a: String", false, true)
    assertEquals(true, CreateConvenienceInitialiser().transform(initialiser)?.throws)
  }

  fun testShouldReturnFailableInitialiserWhenFailableAndNoArguments() {
    val initialiser = Initializer("", true)
    assertEquals(true, CreateConvenienceInitialiser().transform(initialiser)?.isFailable)
    assertEquals(initialiser.parametersList, CreateConvenienceInitialiser().transform(initialiser)?.parameters)
  }
}
