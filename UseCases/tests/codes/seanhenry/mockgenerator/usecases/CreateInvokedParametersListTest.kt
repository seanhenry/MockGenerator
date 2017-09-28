package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateInvokedParametersListTest: TestCase() {

  fun testShouldTransformName() {
    val property = CreateInvokedParametersList().transform("name", listOf("param: Type"))
    assertEquals("invokedNameParametersList", property?.name)
  }

  fun testShouldTransformLongName() {
    val property = CreateInvokedParametersList().transform("longName", listOf("param: Type"))
    assertEquals("invokedLongNameParametersList", property?.name)
  }
}
