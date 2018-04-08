package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class NoArgumentFailableInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initialiser.Builder().failable().build()
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
