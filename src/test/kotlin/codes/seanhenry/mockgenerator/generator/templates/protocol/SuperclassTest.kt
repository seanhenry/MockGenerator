package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class SuperclassTest : GeneratorTestTemplate() {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .superclass {
              it.initializer {
                it.parameter("a1") { it.type("Int") }
                    .parameter("a2") { it.type("Int") }
              }
                  .property("a") { it.type("Int").readonly() }
                  .method("methodA") { }
                  .superclass {
                    it.initializer { it.parameter("b") { it.type("Int") } }
                        .property("b") { it.type("Int").readonly() }
                        .method("methodB") { }
                  }
            }
            .build()
    )
  }
}
