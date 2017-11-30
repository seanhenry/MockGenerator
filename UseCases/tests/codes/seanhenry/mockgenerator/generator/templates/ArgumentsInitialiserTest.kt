package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ArgumentsInitialiserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setInitialiser(
        Initialiser("a: Int, b: String, _ c: String?", false)
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(a: 0, b: "", nil)
      }
      """.trimIndent()
  }
}
