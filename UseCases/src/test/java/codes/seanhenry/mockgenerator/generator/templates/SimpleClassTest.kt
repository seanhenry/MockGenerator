package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SimpleClassTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.addClassProperties(
        Property.Builder("property")
            .type().optional { it.type("String") }
            .build()
    )
    generator.addClassMethods(
        Method.Builder("method").build(),
        Method.Builder("anotherMethod").build()
    )
  }
}
