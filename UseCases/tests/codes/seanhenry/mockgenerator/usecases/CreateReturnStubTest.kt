package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateReturnStubTest: TestCase() {

  fun testTransformsName() {
    val stub = CreateReturnStub().transform("name", "Type")
    assertEquals("stubbedNameResult", stub.name)
  }

  fun testTransformsLongName() {
    val stub = CreateReturnStub().transform("longName", "Type")
    assertEquals("stubbedLongNameResult", stub.name)
  }

  fun testTransformsTypeToIUO() {
    val stub = CreateReturnStub().transform("name", "Type")
    assertEquals("Type!", stub.type)
  }

  fun testTransformsOptionalTypeToIUO() {
    val stub = CreateReturnStub().transform("name", "String?")
    assertEquals("String!", stub.type)
  }

  fun testDoesNotTransformIUO() {
    val stub = CreateReturnStub().transform("name", "Type!")
    assertEquals("Type!", stub.type)
  }

  fun testTransformsDoubleOptionalToIUO() {
    val stub = CreateReturnStub().transform("name", "Type??")
    assertEquals("Type!", stub.type)
  }

  fun testTransformsDoubleIUOToIUO() {
    val stub = CreateReturnStub().transform("name", "Type!!")
    assertEquals("Type!", stub.type)
  }

  fun testTransformsIUOOptionalToIUO() {
    val stub = CreateReturnStub().transform("name", "Type!?")
    assertEquals("Type!", stub.type)
  }
}
