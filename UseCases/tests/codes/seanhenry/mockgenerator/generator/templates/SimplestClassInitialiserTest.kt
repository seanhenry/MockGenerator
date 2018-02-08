package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class SimplestClassInitialiserTest: MockGeneratorTestTemplate {
  override fun build(generator: MockGenerator) {
    generator.setClassInitialisers(
      Initialiser("a: Int, b: String", false),
      Initialiser("a: Int, b: String, c: UInt", false),
      Initialiser("a: String", false)
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
