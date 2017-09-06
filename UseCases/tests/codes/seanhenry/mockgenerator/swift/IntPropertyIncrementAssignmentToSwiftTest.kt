package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class IntPropertyIncrementAssignmentToSwiftTest : TestCase() {

  fun testTransformsToIncrementAssignment() {
    val property = IntPropertyDeclaration("name", 100)
    val result = IntPropertyIncrementAssignmentToSwift().transform(property)
    Assert.assertEquals("name += 1", result)
  }

  fun testTransformsToAnotherIncrementAssignment() {
    val property = IntPropertyDeclaration("anotherName", 0)
    val result = IntPropertyIncrementAssignmentToSwift().transform(property)
    Assert.assertEquals("anotherName += 1", result)
  }
}
