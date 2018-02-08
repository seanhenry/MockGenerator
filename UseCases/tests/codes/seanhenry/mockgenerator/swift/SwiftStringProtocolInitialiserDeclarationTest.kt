package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initialiser
import junit.framework.TestCase

class SwiftStringProtocolInitialiserDeclarationTest: TestCase() {

  fun testShouldCreateRequiredEmptyInitialiser() {
    val initialiser = Initialiser("", false, false, true)
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }

  fun testShouldCopyInitialiserSignature() {
    val initialiser = Initialiser("a: String?, b: Int, c: () -> ()", false, false, true)
    assertEquals("required init(a: String?, b: Int, c: () -> ())", SwiftStringProtocolInitialiserDeclaration().transform(initialiser))
  }
}
