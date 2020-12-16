package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class MultipleOverloadingProtocolsTest : GeneratorTestTemplate() {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .protocol {
              it.method("inheriting") {}
                  .method("inherited") { it.parameter("overloaded") { it.type("Int") } }
            }
            .protocol {
              it.method("inherited") { it.parameter("method") { it.type("String") } }
            }
            .build()
    )
  }
}
