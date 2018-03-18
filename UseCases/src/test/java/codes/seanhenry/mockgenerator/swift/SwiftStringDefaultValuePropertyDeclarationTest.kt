package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringDefaultValuePropertyDeclarationTest: TestCase() {

  fun testTransformsToPropertyDeclarationWithoutDefaultValue() {
    val declaration = PropertyDeclaration("name", "Type")
    assertEquals("var name: Type", SwiftStringDefaultValuePropertyDeclaration().transform(declaration, null))
  }

  fun testTransformsToPropertyWithDefaultValue() {
    val declaration = PropertyDeclaration("otherName", "OtherType")
    assertEquals("var otherName: OtherType = value", SwiftStringDefaultValuePropertyDeclaration().transform(declaration, "value"))
  }
}
