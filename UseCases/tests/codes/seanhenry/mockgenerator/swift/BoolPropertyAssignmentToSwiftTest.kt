package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class BoolPropertyAssignmentToSwiftTest : TestCase() {

  fun testTransformsToFalseAssignment() {
    val property = BoolPropertyDeclaration("name", true)
    val result = BoolPropertyAssignmentToSwift().transform(property, false)
    Assert.assertEquals("name = false", result)
  }

  fun testTransformsToTrueAssignment() {
    val property = BoolPropertyDeclaration("name", false)
    val result = BoolPropertyAssignmentToSwift().transform(property, true)
    Assert.assertEquals("name = true", result)
  }
}
