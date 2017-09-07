package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreatePropertyGetterStubTest : TestCase() {

  fun testTransformsName() {
    val property = CreatePropertyGetterStub().transform("name", "")
    assertEquals("stubbedName", property.name)
  }

  fun testTransformsLongName() {
    val property = CreatePropertyGetterStub().transform("longName", "")
    assertEquals("stubbedLongName", property.name)
  }
}
