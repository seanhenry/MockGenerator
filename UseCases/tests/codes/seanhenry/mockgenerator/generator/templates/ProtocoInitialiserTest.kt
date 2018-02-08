package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ProtocoInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.setInitialiser(
        Initialiser("", false, false, true)
    )
  }

  override fun getExpected(): String {
    return """
      required init() {}
      """.trimIndent()
  }
}
