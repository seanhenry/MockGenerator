package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringCastReturnPropertyTest: TestCase() {

  fun testShouldForceCastNonOptionalReturnValue() {
    val property = PropertyDeclaration("name", "")
    val result = SwiftStringCastReturnProperty().transform(property, "T")
    assertEquals("return name as! T", result)
  }

  fun testShouldOptionallyCastOptionalReturnValue() {
    val property = PropertyDeclaration("name", "")
    val result = SwiftStringCastReturnProperty().transform(property, "T?")
    assertEquals("return name as? T", result)
  }

  fun testShouldOptionallyCastIUOReturnValue() {
    val property = PropertyDeclaration("name", "")
    val result = SwiftStringCastReturnProperty().transform(property, "T!")
    assertEquals("return name as? T", result)
  }
}
