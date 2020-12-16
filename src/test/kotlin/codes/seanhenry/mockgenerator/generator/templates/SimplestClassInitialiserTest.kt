package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SimplestClassInitialiserTest: MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.setClassInitialisers(
        Initializer.Builder()
            .parameter("a") { it.type("Int") }
            .parameter("b") { it.type("String") }
            .build(),
        Initializer.Builder()
            .parameter("a") { it.type("Int") }
            .parameter("b") { it.type("String") }
            .parameter("c") { it.type("UInt") }
            .build(),
        Initializer.Builder()
            .parameter("a") { it.type("String") }
            .build()
    )
  }
}
