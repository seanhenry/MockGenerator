package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringThrowErrorTest: TestCase() {

  fun testShouldReturnEmptyStringForEmptyDeclaration() {
    assertEquals("", SwiftStringThrowError().transform(PropertyDeclaration.EMPTY))
  }

  fun testShouldReturnIfStatementForValidDeclaration() {
    val expected = """
      if let error = stubbedError {
      throw error
      }
      """.trimIndent()
    val property = PropertyDeclaration("stubbedError", "Error?")
    assertEquals(expected, SwiftStringThrowError().transform(property))
  }
}
