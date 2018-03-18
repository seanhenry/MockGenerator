package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ProtocolInitialiserTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Initialiser("", false, false, true),
        Initialiser("a: String", false, false, true),
        Initialiser("b: String", true, false, true),
        Initialiser("c: String", true, true, true)
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
