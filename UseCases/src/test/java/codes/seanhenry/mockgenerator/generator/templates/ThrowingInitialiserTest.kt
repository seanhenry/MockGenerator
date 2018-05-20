package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ThrowingInitialiserTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initializer.Builder()
            .parameter("a") { it.type("String") }
            .throws()
            .build()
    )
  }
}
