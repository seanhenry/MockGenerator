package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringImplicitValuePropertyDeclarationTest: TestCase() {

  fun testTransformsToPropertyDeclaration() {
    val declaration = PropertyDeclaration("name", "Type")
    assertEquals("var name = value", SwiftStringImplicitValuePropertyDeclaration().transform(declaration, "value"))
  }

  fun testTransformsToAnotherPropertyDeclaration() {
    val declaration = PropertyDeclaration("otherName", "Type")
    assertEquals("var otherName = otherValue", SwiftStringImplicitValuePropertyDeclaration().transform(declaration, "otherValue"))
  }
}
