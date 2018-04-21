package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initializer
import junit.framework.TestCase

class SwiftStringProtocolInitializerDeclarationTest: TestCase() {

  fun testShouldCreateRequiredEmptyInitialiser() {
    val initializer = Initializer.Builder()
        .protocol()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  fun testShouldCopyInitialiserSignature() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type().optional { it.type("String") } }
        .parameter("b") { it.type("Int") }
        .parameter("c") { it.type().function { } }
        .protocol()
        .build()
    assertEquals("required init(a: String?, b: Int, c: () -> ())", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  fun testShouldRemoveFailableDeclaration() {
    val initializer = Initializer.Builder()
        .failable()
        .protocol()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  fun testShouldRemoveThrowsClause() {
    val initializer = Initializer.Builder()
        .throws()
        .protocol()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }
}
