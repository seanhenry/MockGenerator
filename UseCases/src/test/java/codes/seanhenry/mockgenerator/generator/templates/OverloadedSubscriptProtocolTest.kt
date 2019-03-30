package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Subscript
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.MockTransformer

class OverloadedSubscriptProtocolTest: MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.add(
        Subscript.Builder(TypeIdentifier("Int"))
            .readonly()
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("a") { it.type("Int") }
            .readonly()
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("b", "b") { it.type("Int") }
            .readonly()
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("c", "c") { it.type("Int") }
            .readonly()
            .build(),
        Subscript.Builder(TypeIdentifier("Int"))
            .parameter("c", "c") { it.type("String") }
            .readonly()
            .build()
    )
  }
}