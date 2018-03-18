package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.TEST_PARAMETER
import junit.framework.TestCase

class CreateInvokedParametersTest: TestCase() {

  fun testShouldTransformName() {
    val property = transformName("name")
    assertEquals("invokedNameParameters", property?.name)
  }

  fun testShouldTransformLongName() {
    val property = transformName("longName")
    assertEquals("invokedLongNameParameters", property?.name)
  }

  private fun transformName(name: String) = CreateInvokedParameters().transform(name, listOf(TEST_PARAMETER))
}
