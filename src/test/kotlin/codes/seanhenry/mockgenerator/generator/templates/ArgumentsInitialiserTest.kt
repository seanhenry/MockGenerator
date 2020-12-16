package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ArgumentsInitialiserTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initializer.Builder()
            .parameter("a") { it.type("Int") }
            .parameter("b") { it.type("String") }
            .parameter("_", "c") { it.type().optional { it.type("String") } }
            .build()
    )
  }
}
