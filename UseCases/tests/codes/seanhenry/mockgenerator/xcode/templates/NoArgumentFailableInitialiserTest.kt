package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

class NoArgumentFailableInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: XcodeMockGenerator) {
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
