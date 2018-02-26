package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Type
import junit.framework.TestCase

class CreatePropertyGetterStubTest : TestCase() {

  fun testTransformsName() {
    val property = CreatePropertyGetterStub().transform("name", "", Type(""))
    assertEquals("stubbedName", property.name)
  }

  fun testTransformsLongName() {
    val property = CreatePropertyGetterStub().transform("longName", "", Type(""))
    assertEquals("stubbedLongName", property.name)
  }
}
