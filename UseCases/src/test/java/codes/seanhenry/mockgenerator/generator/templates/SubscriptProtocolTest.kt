package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Subscript
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SubscriptProtocolTest: MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.add(
        Subscript.Builder(TypeIdentifier("Int"))
            .build()
    )
  }
}