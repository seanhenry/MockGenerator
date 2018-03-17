package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.generator.MockTransformer

interface MockGeneratorTestTemplate {

  fun build(generator: MockTransformer)
  fun getExpected(): String
}
