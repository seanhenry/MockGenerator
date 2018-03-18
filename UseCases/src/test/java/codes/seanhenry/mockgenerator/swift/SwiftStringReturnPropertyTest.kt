package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringReturnPropertyTest: TestCase() {

  fun testTransformsToReturnStatement() {
    val property = PropertyDeclaration("name", "Type")
    assertEquals("return name", SwiftStringReturnProperty().transform(property))
  }

  fun testTransformsToAnotherReturnStatement() {
    val property = PropertyDeclaration("otherName", "Type")
    assertEquals("return otherName", SwiftStringReturnProperty().transform(property))
  }
}
