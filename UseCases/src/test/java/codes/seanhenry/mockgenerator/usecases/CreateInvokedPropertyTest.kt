package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateInvokedPropertyTest: TestCase() {

  fun testTransformsName() {
    val property = CreateInvokedProperty().transform("name", "")
    assertEquals("invokedName", property.name)
  }

  fun testTransformsLongName() {
    val property = CreateInvokedProperty().transform("longName", "")
    assertEquals("invokedLongName", property.name)
  }

  fun testTransformsType() {
    val property = CreateInvokedProperty().transform("", "Type")
    assertEquals("Type", property.type)
  }

  fun testTransformsAnotherType() {
    val property = CreateInvokedProperty().transform("", "OtherType")
    assertEquals("OtherType", property.type)
  }
}
