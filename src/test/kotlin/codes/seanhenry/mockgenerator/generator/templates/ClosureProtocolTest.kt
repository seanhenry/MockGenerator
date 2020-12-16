package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ClosureProtocolTest : MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("map")
            .parameter("closure") { param ->
              param.type().function {}
            }
            .build(),
        Method.Builder("flatMap")
            .parameter("closure") { param ->
              param.type().function { func -> func.returnType("Void") }
            }
            .build(),
        Method.Builder("filter")
            .parameter("closure") { param ->
              param.type().function { func ->
                func.argument("String").returnType("Bool")
              }
            }
            .build(),
        Method.Builder("multi")
            .parameter("animations") { param ->
              param.type().function { func -> func.argument("Int") }
            }
            .parameter("completion") { param ->
              param.type().function { func -> func.argument("Bool") }
            }
            .build(),
        Method.Builder("optional")
            .parameter("animations") { param ->
              param.type().optional { opt ->
                opt.type().function { it.argument("Int") }
              }
            }
            .parameter("completion") { param ->
              param.type().optional { opt ->
                opt.type().function { it.argument("Bool") }
              }
            }
            .build(),
        Method.Builder("optionalParam")
            .parameter("_", "closure") { param ->
              param.type().function { func ->
                func.argument().optional { it.type("String") }
              }
            }
            .build(),
        Method.Builder("optionalParams")
            .parameter("_", "closure") { param ->
              param.type().function { func ->
                func.argument().optional { it.type("String") }
                    .argument().optional { it.type("Int") }
              }
            }
            .build(),
        Method.Builder("optionalArrayParams")
            .parameter("_", "closure") { param ->
              param.type().function { func ->
                func.argument().optional { it.type().array { it.type("String") } }
                    .argument().array { it.type("UInt") }
              }
            }
            .build(),
        Method.Builder("doNotSuppressWarning1")
            .parameter("_", "closure") { param ->
              param.type().function { }
            }
            .build(),
        Method.Builder("doNotSuppressWarning2")
            .parameter("_", "closure") { param ->
              param.type().function { it.returnType("Void") }
            }
            .build(),
        Method.Builder("doNotSuppressWarning3")
            .parameter("_", "closure") { param ->
              param.type().function { it.returnType("(Void)") }
            }
            .build(),
        Method.Builder("suppressWarning1")
            .parameter("_", "closure") { param ->
              param.type().function { it.returnType("String") }
            }
            .build(),
        Method.Builder("suppressWarning2")
            .parameter("_", "closure") { param ->
              param.type().function { it.returnType("(String)") }
            }
            .build(),
        Method.Builder("suppressWarning3")
            .parameter("_", "closure") { param ->
              param.type().function { func ->
                func.returnType().optional { it.type("String") }
              }
            }
            .build(),
        Method.Builder("suppressWarning4")
            .parameter("_", "closure") { param ->
              param.type().function { func ->
                func.returnType().optional { it.type("String").unwrapped() }
              }
            }
            .build()
    )
  }
}
