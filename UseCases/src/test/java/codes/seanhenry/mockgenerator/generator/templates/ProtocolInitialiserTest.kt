package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ProtocolInitialiserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Initialiser.Builder()
            .protocol()
            .build(),
        Initialiser.Builder()
            .parameter("a") { it.type("String") }
            .protocol()
            .build(),
        Initialiser.Builder()
            .parameter("b") { it.type("String") }
            .failable()
            .protocol()
            .build(),
        Initialiser.Builder()
            .parameter("c") { it.type("String") }
            .failable()
            .throws()
            .protocol()
            .build()
    )
  }

  override fun getExpected(): String {
    return """
      required init() {
      }
      required init(a: String) {
      }
      required init(b: String) {
      }
      required init(c: String) {
      }
      """.trimIndent()
  }
}
