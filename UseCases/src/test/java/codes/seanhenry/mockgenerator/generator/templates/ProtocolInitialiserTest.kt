package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ProtocolInitialiserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Initializer.Builder()
            .protocol()
            .build(),
        Initializer.Builder()
            .parameter("a") { it.type("String") }
            .protocol()
            .build(),
        Initializer.Builder()
            .parameter("b") { it.type("String") }
            .failable()
            .protocol()
            .build(),
        Initializer.Builder()
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
