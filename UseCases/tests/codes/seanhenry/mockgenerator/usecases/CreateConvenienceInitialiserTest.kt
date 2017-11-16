package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.InitialiserMethod
import junit.framework.TestCase

class CreateConvenienceInitialiserTest: TestCase() {

  fun testShouldReturnNilForInitialiserWithNoArguments() {
    val initialiser = InitialiserMethod("", "init()")
    assertNull(CreateConvenienceInitialiser().transform(initialiser))
  }

  fun testShouldReturnOriginalInitialiserWith1Argument() {
    val initialiser = InitialiserMethod("a: String", "init(a: String)")
    assertEquals(initialiser.parametersList, CreateConvenienceInitialiser().transform(initialiser)?.parameters)
  }
}
