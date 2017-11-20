package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.xcode.MockGenerator

interface MockGeneratorTestTemplate {

  fun build(generator: MockGenerator)
  fun getExpected(): String
}
