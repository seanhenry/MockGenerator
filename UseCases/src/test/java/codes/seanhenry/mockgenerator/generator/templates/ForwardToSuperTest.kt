package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ForwardToSuperTest : MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.addClassMethods(
        Method.Builder("method")
            .build(),
        Method.Builder("method")
            .parameter("a") { it.type("Int") }
            .parameter("_", "b") { it.type("Int") }
            .parameter("c", "d") { it.type("Int") }
            .build(),
        Method.Builder("returnMethod")
            .returnType("Int")
            .build(),
        Method.Builder("forwardNoStubs")
            .parameter("a") { it.type().function { } }
            .throws()
            .build(),
        Method.Builder("throwing")
            .throws()
            .returnType("Int")
            .build()
    )
    generator.add(Method.Builder("protocolMethod").build())
  }
}
