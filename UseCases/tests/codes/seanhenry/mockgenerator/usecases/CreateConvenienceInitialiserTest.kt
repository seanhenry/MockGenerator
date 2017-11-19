package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initialiser
import junit.framework.TestCase

class CreateConvenienceInitialiserTest: TestCase() {

  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initialiser = Initialiser("", false)
    assertNull(CreateConvenienceInitialiser().transform(initialiser))
  }

  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initialiser = Initialiser("a: String", false)
    assertEquals(initialiser.parametersList, CreateConvenienceInitialiser().transform(initialiser)?.parameters)
  }

  fun testShouldReturnOptionalInitialiserWhenFailable() {
    val initialiser = Initialiser("a: String", true)
    assertEquals(true, CreateConvenienceInitialiser().transform(initialiser)?.isFailable)
  }
}
