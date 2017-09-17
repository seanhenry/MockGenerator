package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringInitializedArrayPropertyDeclarationTest: TestCase() {

  fun testShouldCreateInitialzedArray() {
    val property = PropertyDeclaration("name", "Type")
    assertEquals("var name = [Type]()", SwiftStringInitializedArrayPropertyDeclaration().transform(property))
  }

  fun testShouldCreateAnotherInitialzedArray() {
    val property = PropertyDeclaration("anotherName", "AnotherType")
    assertEquals("var anotherName = [AnotherType]()", SwiftStringInitializedArrayPropertyDeclaration().transform(property))
  }
}
