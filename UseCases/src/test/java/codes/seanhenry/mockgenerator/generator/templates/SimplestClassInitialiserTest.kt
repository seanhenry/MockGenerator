package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SimplestClassInitialiserTest: MockGeneratorTestTemplate {
  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initialiser.Builder()
            .parameter("a") { it.type("Int") }
            .parameter("b") { it.type("String") }
            .build(),
        Initialiser.Builder()
            .parameter("a") { it.type("Int") }
            .parameter("b") { it.type("String") }
            .parameter("c") { it.type("UInt") }
            .build(),
        Initialiser.Builder()
            .parameter("a") { it.type("String") }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(a: "")
      }
      """.trimIndent()
  }
}
