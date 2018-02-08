package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class NoArgumentFailableInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setClassInitialiser(
        Initialiser("", true)
    )
  }

  override fun getExpected(): String {
    return """
      override init() {
      super.init()!
      }
      """.trimIndent()
  }
}
