package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class GenericReturnValueTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("generic1")
            .genericParameter("T")
            .returnType("T")
            .build(),
        Method.Builder("generic2")
            .genericParameter("T")
            .returnType().optional { it.type("T") }
            .build(),
        Method.Builder("generic3")
            .genericParameter("T")
            .returnType().optional { it.unwrapped().type("T") }
            .build(),
        Method.Builder("genericArray")
            .genericParameter("T")
            .returnType().array { it.type("T") }
            .build()
    )
  }
}
