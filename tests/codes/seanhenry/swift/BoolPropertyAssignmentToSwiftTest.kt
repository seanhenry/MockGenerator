package codes.seanhenry.swift

import codes.seanhenry.entities.BoolPropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class BoolPropertyAssignmentToSwiftTest : TestCase() {

  fun testTransformsToFalseAssignment() {
    val property = BoolPropertyDeclaration("name", false)
    val result = BoolPropertyAssignmentToSwift().transform(property)
    Assert.assertEquals("name = false", result)
  }

  fun testTransformsToTrueAssignment() {
    val property = BoolPropertyDeclaration("name", true)
    val result = BoolPropertyAssignmentToSwift().transform(property)
    Assert.assertEquals("name = true", result)
  }
}
