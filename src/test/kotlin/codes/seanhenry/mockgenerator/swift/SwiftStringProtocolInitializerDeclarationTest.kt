package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initializer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SwiftStringProtocolInitializerDeclarationTest {

  @Test
  fun testShouldCreateRequiredEmptyInitialiser() {
    val initializer = Initializer.Builder()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  @Test
  fun testShouldCopyInitialiserSignature() {
    val initializer = Initializer.Builder()
        .parameter("a") { it.type().optional { it.type("String") } }
        .parameter("b") { it.type("Int") }
        .parameter("c") { it.type().function { } }
        .build()
    assertEquals("required init(a: String?, b: Int, c: () -> ())", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  @Test
  fun testShouldRemoveFailableDeclaration() {
    val initializer = Initializer.Builder()
        .failable()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }

  @Test
  fun testShouldRemoveThrowsClause() {
    val initializer = Initializer.Builder()
        .throws()
        .build()
    assertEquals("required init()", SwiftStringProtocolInitialiserDeclaration().transform(initializer))
  }
}
