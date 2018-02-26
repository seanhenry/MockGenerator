package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Type
import junit.framework.TestCase

class CreateMethodReturnStubTest : TestCase() {

  fun testTransformsName() {
    val stub = CreateMethodReturnStub().transform("name", "Type", Type("Type"))
    assertEquals("stubbedNameResult", stub.name)
  }

  fun testTransformsLongName() {
    val stub = CreateMethodReturnStub().transform("longName", "Type", Type("Type"))
    assertEquals("stubbedLongNameResult", stub.name)
  }
}
