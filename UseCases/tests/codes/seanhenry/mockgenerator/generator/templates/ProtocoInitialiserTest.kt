package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ProtocoInitialiserTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.add(
        Initialiser("", false, false, true),
        Initialiser("a: String", false, false, true)
    )
  }

  override fun getExpected(): String {
    return """
      required init() {}
      required init(a: String) {}
      """.trimIndent()
  }
}
