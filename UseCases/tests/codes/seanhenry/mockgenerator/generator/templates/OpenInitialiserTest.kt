package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class OpenInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setClassInitialiser(
        Initialiser("a: String?", false, false)
    )
    generator.setScope("open")
  }

  override fun getExpected(): String {
    return """
      public convenience init() {
      self.init(a: nil)
      }
      """.trimIndent()
  }
}
