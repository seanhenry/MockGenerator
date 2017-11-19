package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.xcode.MockGenerator

class NoArgumentFailableInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setInitialiser(
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
