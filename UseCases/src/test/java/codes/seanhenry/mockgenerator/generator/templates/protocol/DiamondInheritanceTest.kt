package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class DiamondInheritanceTest: GeneratorTestTemplate() {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .protocol {
              it.initializer { it.parameter("i1") { it.type("Int") } }
                  .property("p1") { it.type("Int").readonly() }
                  .method("m1") {}
                  .protocol {
                    it.initializer { it.parameter("i1") { it.type("Int") } }
                        .property("p2") { it.type("Int").readonly() }
                        .method("m3") {}
                  }
            }
            .protocol {
              it.initializer { it.parameter("i3") { it.type("Int") } }
                .property("p3") { it.type("Int").readonly() }
                .method("m3") {}
                .protocol {
                  it.initializer { it.parameter("i2") { it.type("Int") } }
                      .property("p2") { it.type("Int").readonly() }
                      .method("m2") {}
                }
            }
            .build()
    )
  }
}
