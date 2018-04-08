package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class OpenInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initialiser.Builder()
            .parameter("a") { it.type().optional { it.type("String") } }
            .build()
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
