package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class MethodParameterTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("oneParam")
            .parameter("param0") { it.type("Int") }
            .build(),
        Method.Builder("twoParam")
            .parameter("param0") { it.type("Int") }
            .parameter("param1") { it.type("String") }
            .build(),
        Method.Builder("optionalParam")
            .parameter("param0") { it.type().optional { it.type("Int") } }
            .build(),
        Method.Builder("iuoParam")
            .parameter("param0") { it.type().optional { it.unwrapped().type("Int") } }
            .build(),
        Method.Builder("noLabelParam")
            .parameter("_", "name0") { it.type().optional { it.unwrapped().type("Int") } }
            .build(),
        Method.Builder("nameAndLabelParam")
            .parameter("label0", "name0") { it.type().optional { it.unwrapped().type("Int") } }
            .build(),
        Method.Builder("closureParam")
            .parameter("closure") { it.type().function { } }
            .build()
    )
  }
}
