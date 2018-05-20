package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class OverloadProtocolTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Property.Builder("int")
            .type("Int")
            .build()
    )
    generator.add(
        Method.Builder("int")
            .parameter("adding") { it.type("Int") }
            .returnType("Int")
            .build(),
        Method.Builder("setValue")
            .parameter("_", "string") { it.type("String") }
            .parameter("forKey", "key") { it.type("String") }
            .build(),
        Method.Builder("setValue")
            .parameter("_", "int") { it.type("Int") }
            .parameter("forKey", "key") { it.type("String") }
            .build(),
        Method.Builder("set")
            .parameter("value") { it.type("String") }
            .build(),
        Method.Builder("set")
            .parameter("value") { it.type("Int") }
            .build(),
        Method.Builder("animate")
            .returnType("Bool")
            .build(),
        Method.Builder("animate")
            .parameter("withDuration", "duration") { it.type("TimeInterval") }
            .build(),
        Method.Builder("animate")
            .parameter("withDuration", "duration") { it.type("TimeInterval") }
            .parameter("delay") { it.type("TimeInterval") }
            .build(),
        Method.Builder("specialCharacters")
            .parameter("_", "tuple") { it.type("(String, Int)") }
            .build(),
        Method.Builder("specialCharacters")
            .parameter("_", "tuple") { it.type("(UInt, Float)") }
            .build()
    )
  }
}
