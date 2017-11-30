package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.generator.MockGenerator

interface MockGeneratorTestTemplate {

  fun build(generator: MockGenerator)
  fun getExpected(): String
}
