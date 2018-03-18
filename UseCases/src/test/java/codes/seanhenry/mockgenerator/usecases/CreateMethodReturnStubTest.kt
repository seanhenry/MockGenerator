package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateMethodReturnStubTest : TestCase() {

  fun testTransformsName() {
    val stub = CreateMethodReturnStub().transform("name", "Type")
    assertEquals("stubbedNameResult", stub.name)
  }

  fun testTransformsLongName() {
    val stub = CreateMethodReturnStub().transform("longName", "Type")
    assertEquals("stubbedLongNameResult", stub.name)
  }
}
