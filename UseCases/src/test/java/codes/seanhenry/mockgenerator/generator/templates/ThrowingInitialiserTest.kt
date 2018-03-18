package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ThrowingInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
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
