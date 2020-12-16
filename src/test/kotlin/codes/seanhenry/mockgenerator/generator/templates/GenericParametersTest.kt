package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class GenericParametersTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("generic")
            .genericParameter("T")
            .parameter("a") { it.type("T") }
            .build(),
        Method.Builder("generic")
            .genericParameter("T")
            .parameter("array") { it.type().array { it.type("T") } }
            .build(),
        Method.Builder("generic")
            .genericParameter("T")
            .parameter("b") { it.type().typeIdentifier("T") { it.nest("Type") } }
            .build(),
        Method.Builder("generic")
            .genericParameter("T")
            .genericParameter("U")
            .parameter("c") { it.type().optional { it.type("T") } }
            .parameter("d") { it.type().optional { it.unwrapped().type("U") } }
            .build()
    )
  }
}
