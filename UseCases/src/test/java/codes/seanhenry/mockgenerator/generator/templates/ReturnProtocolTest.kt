package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ReturnProtocolTest: MockGeneratorTestTemplate() {
  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("returnType")
            .returnType("String")
            .build(),
        Method.Builder("returnTuple")
            .returnType("(String, Int?)")
            .build(),
        Method.Builder("returnLabelledTuple")
            .returnType("(s: String, i: Int?)")
            .build(),
        Method.Builder("returnOptional")
            .returnType().optional { it.type("Int") }
            .build(),
        Method.Builder("returnIUO")
            .returnType().optional { it.unwrapped().type("UInt") }
            .build(),
        Method.Builder("returnGeneric")
            .returnType().optional { it.verbose().type("String") }
            .build(),
        Method.Builder("returnOptionalGeneric")
            .returnType().optional { it.type().optional { it.verbose().type("String") } }
            .build(),
        Method.Builder("returnClosure")
            .returnType().function { }
            .build(),
        Method.Builder("returnComplicatedClosure")
            .returnType().bracket().function { func ->
              func.argument("String")
                  .argument().optional { it.type("Int") }
                  .returnType().bracket().type("UInt")
            }
            .build(),
        Method.Builder("returnOptionalClosure")
            .returnType().optional { it.type().function { } }
            .build(),
        Method.Builder("returnExplicitVoid")
            .returnType("Void")
            .build(),
        Method.Builder("returnClosure")
            .returnType().bracket().function { }
            .build(),
        Method.Builder("returnClosureArgs")
            .returnType().function { func ->
              func.argument("Int")
                  .argument("String")
                  .returnType("(String)")
            }
            .build(),
        Method.Builder("closureA")
            .returnType().function { }
            .build(),
        Method.Builder("closureB")
            .returnType().function { func ->
              func.returnType().bracket().type("Void")
            }
            .build(),
        Method.Builder("closureC")
            .returnType().function { func ->
              func.returnType().type("Void")
            }
            .build(),
        Method.Builder("closureD")
            .returnType().function { func ->
              func.argument("String")
                  .argument("Int")
            }
            .build(),
        Method.Builder("closureE")
            .returnType().function { func ->
              func.returnType().bracket().type("String")
            }
            .build()
    )
  }
}
