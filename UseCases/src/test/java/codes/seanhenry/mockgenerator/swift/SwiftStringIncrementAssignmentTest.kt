package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class SwiftStringIncrementAssignmentTest : TestCase() {

  fun testTransformsToIncrementAssignment() {
    val property = PropertyDeclaration("name", "Int")
    val result = SwiftStringIncrementAssignment().transform(property)
    Assert.assertEquals("name += 1", result)
  }

  fun testTransformsToAnotherIncrementAssignment() {
    val property = PropertyDeclaration("anotherName", "OtherType")
    val result = SwiftStringIncrementAssignment().transform(property)
    Assert.assertEquals("anotherName += 1", result)
  }
}
