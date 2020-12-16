package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ScopeProtocolTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.setScope("open")
    generator.add(
        Property.Builder("variable")
            .type("Object")
            .build()
    )
    generator.add(
        Method.Builder("method")
            .parameter("param") { it.type("Object") }
            .parameter("closure") { it.type().function { } }
            .returnType("Object")
            .build()
    )
  }
}
