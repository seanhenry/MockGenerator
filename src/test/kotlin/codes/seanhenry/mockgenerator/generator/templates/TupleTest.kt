package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class TupleTest: MockGeneratorTestTemplate() {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("a")
            .parameter("a") { it.type().tuple { } }
            .build()
    )
  }
}