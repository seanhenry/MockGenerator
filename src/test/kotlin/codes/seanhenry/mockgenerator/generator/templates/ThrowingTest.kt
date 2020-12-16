package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ThrowingTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("throwingMethod")
            .throws()
            .build(),
        Method.Builder("throwingReturnMethod")
            .returnType("String")
            .throws()
            .build(),
        Method.Builder("throwingClosure")
            .parameter("closure") { param ->
              param.type().function { it.throws() }
            }
            .build(),
        Method.Builder("throwingClosureArgument")
            .parameter("closure") { param ->
              param.type().function { func ->
                func.argument("String")
                    .returnType("(String)")
                    .throws()
              }
            }
            .build()
    )
  }
}
