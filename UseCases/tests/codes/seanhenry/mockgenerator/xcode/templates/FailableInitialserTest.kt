package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

class FailableInitialserTest : MockGeneratorTestTemplate {

  override fun build(generator: XcodeMockGenerator) {
    generator.setInitialiser(
      Initialiser("a: String", true)
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
