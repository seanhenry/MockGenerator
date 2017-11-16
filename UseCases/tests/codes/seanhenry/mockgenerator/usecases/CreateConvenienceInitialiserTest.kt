package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initialiser
import junit.framework.TestCase

class CreateConvenienceInitialiserTest: TestCase() {

  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initialiser = Initialiser("")
    assertNull(CreateConvenienceInitialiser().transform(initialiser))
  }

  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initialiser = Initialiser("a: String")
    assertEquals(initialiser.parametersList, CreateConvenienceInitialiser().transform(initialiser)?.parameters)
  }
}
