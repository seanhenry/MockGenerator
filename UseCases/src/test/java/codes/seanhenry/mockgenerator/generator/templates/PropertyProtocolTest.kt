package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class PropertyProtocolTest : MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.add(
        Property.Builder("readWrite")
            .type("String")
            .build(),
        Property.Builder("readOnly")
            .type("Int")
            .readonly()
            .build(),
        Property.Builder("optional")
            .type().optional { it.type("UInt") }
            .build(),
        Property.Builder("unwrapped")
            .type().optional { it.unwrapped().type("String") }
            .build(),
            Property.Builder ("tuple")
            .type().optional { opt ->
                  opt.type().tuple { tuple ->
                    tuple.element("Int")
                        .element().optional { it.type("String") }
                  }
                }
            .build()
        )
    generator.add(
        Method.Builder("method").build()
    )
  }
}
