package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringPropertyAssignmentTest: TestCase() {

  fun testTransformsToPropertyStatement() {
    val property = PropertyDeclaration("name", "Type")
    assertEquals("name = value", SwiftStringPropertyAssignment().transform(property, "value"))
  }

  fun testTransformsToAnotherPropertyStatement() {
    val property = PropertyDeclaration("otherName", "Type")
    assertEquals("otherName = otherValue", SwiftStringPropertyAssignment().transform(property, "otherValue"))
  }
}
