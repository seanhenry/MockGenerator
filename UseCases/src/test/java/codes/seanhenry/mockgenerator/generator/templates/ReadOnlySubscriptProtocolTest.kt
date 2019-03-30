package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Subscript
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ReadOnlySubscriptProtocolTest : MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.add(
        Subscript.Builder(TypeIdentifier.INT).build()
    )
  }
}