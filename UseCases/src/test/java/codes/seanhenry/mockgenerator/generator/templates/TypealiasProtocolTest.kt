package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class TypealiasProtocolTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("typealiasClosure")
            .parameter("closure") { param ->
              param.type("Completion").resolvedType().function { func ->
                func.argument("Int")
                    .returnType("(String)")
              }
            }
            .build(),
        Method.Builder("internalTypealiasClosure")
            .parameter("closure") { param ->
              param.type("T").resolvedType().function { func ->
                func.argument("String")
              }
            }
            .build()
    )
  }
}
