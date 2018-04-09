package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initializer
import junit.framework.TestCase

class SwiftStringProtocolInitializerDeclarationTest: TestCase() {

  fun testShouldCreateRequiredEmptyInitialiser() {
    val initialiser = Initializer("", false, false, true)
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }

  fun testShouldCopyInitialiserSignature() {
    val initialiser = Initializer("a: String?, b: Int, c: () -> ()", false, false, true)
    assertEquals("required init(a: String?, b: Int, c: () -> ())", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }

  fun testShouldRemoveFailableDeclaration() {
    val initialiser = Initializer("", true, false, true)
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }

  fun testShouldRemoveThrowsClause() {
    val initialiser = Initializer("", false, true, true)
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }
}
