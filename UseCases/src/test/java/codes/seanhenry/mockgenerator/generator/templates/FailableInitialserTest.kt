package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class FailableInitialserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initialiser.Builder()
            .parameter("a") { it.type("String") }
            .failable()
            .build()
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
