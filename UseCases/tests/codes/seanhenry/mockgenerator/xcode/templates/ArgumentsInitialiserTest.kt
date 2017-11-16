package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

class ArgumentsInitialiserTest : MockGeneratorTestTemplate {

  override fun build(generator: XcodeMockGenerator) {
    generator.setInitialiser(
        Initialiser("a: Int, b: String, _ c: String?")
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(a: 0, b: "", nil)
      }
      """.trimIndent()
  }
}
