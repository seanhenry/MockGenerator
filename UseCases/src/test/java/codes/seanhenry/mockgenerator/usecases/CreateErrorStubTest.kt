package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class CreateErrorStubTest: TestCase() {

  fun testShouldReturnEmptyDeclarationWhenNoThrow() {
    assertEquals(PropertyDeclaration.EMPTY, CreateErrorStub().transform("name", false))
  }

  fun testShouldReturnErrorDeclarationWhenThrows() {
    val expected = PropertyDeclaration("stubbedNameError", "Error?")
    val transformed = CreateErrorStub().transform("name", true)
    assertEquals(expected.name, transformed.name)
    assertEquals(expected.type, transformed.type)
  }
}
