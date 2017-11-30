package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class FailableInitialserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setInitialiser(
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
