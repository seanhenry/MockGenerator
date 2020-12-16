package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ForwardToSuperTest : MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.addClassProperties(
        Property.Builder("propA")
            .type("Int")
            .build(),
        Property.Builder("readonly")
            .readonly()
            .type("Int")
            .build()
    )
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
            .build(),
        Method.Builder("rethrowing")
            .rethrows()
            .returnType("Int")
            .build()
    )
    generator.add(
        Property.Builder("protocolProperty")
            .type("Int")
            .build(),
        Property.Builder("protocolReadonlyProperty")
            .type("Int")
            .readonly()
            .build()
    )
    generator.add(Method.Builder("protocolMethod").build())
  }
}
