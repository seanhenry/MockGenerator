package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateInvokedPropertyListTest: TestCase() {

  fun testTransformsName() {
    val property = CreateInvokedPropertyList().transform("name", "")
    assertEquals("invokedNameList", property.name)
  }

  fun testTransformsLongName() {
    val property = CreateInvokedPropertyList().transform("longName", "")
    assertEquals("invokedLongNameList", property.name)
  }

  fun testTransformsType() {
    val property = CreateInvokedPropertyList().transform("", "Type")
    assertEquals("Type", property.type)
  }

  fun testTransformsAnotherType() {
    val property = CreateInvokedPropertyList().transform("", "OtherType")
    assertEquals("OtherType", property.type)
  }
}
