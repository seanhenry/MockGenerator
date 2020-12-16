package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class ClassOverridingTest: GeneratorTestTemplate() {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .superclass {
              it.initializer { it.parameter("a") { it.type("Int") } }
                .property("a") { it.type("Int").readonly() }
                .method("methodA") { }
                  .superclass {
                    it.initializer { it.parameter("a") { it.type("Int") } }
                        .property("a") { it.type("Int").readonly() }
                        .method("methodA") { }
                  }
            }
            .build()
    )
  }
}
