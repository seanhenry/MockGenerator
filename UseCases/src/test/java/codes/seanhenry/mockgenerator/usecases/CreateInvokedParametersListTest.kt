package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.TEST_PARAMETER
import junit.framework.TestCase

class CreateInvokedParametersListTest: TestCase() {

  fun testShouldTransformName() {
    val property = CreateInvokedParametersList().transform("name", listOf(TEST_PARAMETER))
    assertEquals("invokedNameParametersList", property?.name)
  }

  fun testShouldTransformLongName() {
    val property = CreateInvokedParametersList().transform("longName", listOf(TEST_PARAMETER))
    assertEquals("invokedLongNameParametersList", property?.name)
  }
}
