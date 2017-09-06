package codes.seanhenry.swift

import codes.seanhenry.entities.BoolPropertyDeclaration
import junit.framework.TestCase
import org.junit.Assert

class BoolPropertyDeclarationToSwiftTest: TestCase() {

  fun testCreatesPropertyDeclarationWithFalseInitializer() {
    val result = BoolPropertyDeclarationToSwift()
        .transform(BoolPropertyDeclaration("property", false))
    Assert.assertEquals("var property = false", result)
  }

  fun testCreatesPropertyDeclarationWithTrueInitializer() {
    val result = BoolPropertyDeclarationToSwift()
        .transform(BoolPropertyDeclaration("property", true))
    Assert.assertEquals("var property = true", result)
  }
}
