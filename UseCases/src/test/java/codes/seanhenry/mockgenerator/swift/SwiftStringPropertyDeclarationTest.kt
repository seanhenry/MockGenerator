package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import junit.framework.TestCase

class SwiftStringPropertyDeclarationTest: TestCase() {

  fun testTransformsToPropertyDeclaration() {
    val declaration = PropertyDeclaration("name", "Type")
    assertEquals("var name: Type", SwiftStringPropertyDeclaration().transform(declaration))
  }

  fun testTransformsToAnotherPropertyDeclaration() {
    val declaration = PropertyDeclaration("otherName", "OtherType")
    assertEquals("var otherName: OtherType", SwiftStringPropertyDeclaration().transform(declaration))
  }

  fun testTransformsEmptyPropertyToEmptyString() {
    assertEquals("", SwiftStringPropertyDeclaration().transform(PropertyDeclaration.EMPTY))
  }
}
