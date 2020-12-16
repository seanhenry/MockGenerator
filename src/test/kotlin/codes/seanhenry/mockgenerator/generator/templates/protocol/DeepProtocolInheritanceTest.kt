package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class DeepProtocolInheritanceTest : GeneratorTestTemplate() {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .protocol {
              it.method("topMost") {}
                  .protocol {
                    it.method("middle") {}
                        .protocol {
                          it.method("deepest") {}
                        }
                        .protocol {
                          it.method("deepestSibling") {}
                        }
                  }
                  .protocol {
                    it.method("middleSibling1") {}
                        .protocol {
                          it.method("deepestCousin") {}
                        }
                  }
                  .protocol {
                    it.method("middleSibling2") {}
                  }

            }
            .protocol {
              it.method("topSibling") {}
            }
            .build()
    )
  }
}
