package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.Generator

class AugmentedClassSubscriptTest : GeneratorTestTemplate() {
  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .superclass {
              it.subscript(TypeIdentifier("Int")) {}
                  .subscript(TypeIdentifier("Int")) {
                    it.parameter("b") {
                      it.type("Int")
                    }
                  }
                  .superclass {
                    it.subscript(TypeIdentifier("Int")) {
                      it.readonly()
                          .parameter("b") {
                            it.type("Int")
                          }
                    }
                  }
            }
            .protocol {
              it.subscript(TypeIdentifier("Int")) {
                it.readonly()
              }
            }
            .build()
    )
  }
}