package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class FailableInitialserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
      Initialiser("a: String", true)
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(a: "")!
      }
      """.trimIndent()
  }
}
