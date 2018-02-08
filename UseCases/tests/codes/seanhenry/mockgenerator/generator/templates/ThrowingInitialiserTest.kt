package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ThrowingInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setClassInitialisers(Initialiser("a: String", false, true))
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      try! self.init(a: "")
      }
      """.trimIndent()
  }
}
