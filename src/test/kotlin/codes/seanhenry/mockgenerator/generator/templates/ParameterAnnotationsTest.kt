package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer


class ParameterAnnotationsTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("escaping").parameter("closure") { param ->
          param.escaping().type().function { }
        }.build(),
        Method.Builder("inOut").parameter("var1") { param ->
          param.inout().type("Int")
        }.build(),
        Method.Builder("autoclosure").parameter("closure") { param ->
          param.annotation("@autoclosure").type().function { }
        }.build(),
        Method.Builder("convention").parameter("closure") { param ->
          param.annotation("@convention(swift)").type().function { }
        }.build()
    )
  }
}
