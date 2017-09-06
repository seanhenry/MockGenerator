package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class IntPropertyDeclarationToSwiftTest: TestCase() {

  fun testTransformsToDeclaration() {
    val property = IntPropertyDeclaration("name", 0)
    val result = IntPropertyDeclarationToSwift().transform(property)
    Assert.assertEquals("var name = 0", result)
  }

  fun testTransformsToAnotherDeclaration() {
    val property = IntPropertyDeclaration("anotherName", 1)
    val result = IntPropertyDeclarationToSwift().transform(property)
    Assert.assertEquals("var anotherName = 1", result)
  }
}
