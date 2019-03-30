package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Subscript
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ClassSubscriptTest: MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.addClassSubscripts(
        Subscript.Builder(TypeIdentifier("Int"))
            .readonly()
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("b") { it.type("Int") }
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("b", "b") { it.type("String") }
            .build()
    )
  }
}