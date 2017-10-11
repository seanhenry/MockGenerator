package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

interface MockGeneratorTestTemplate {

  fun build(generator: XcodeMockGenerator)
  fun getExpected(): String
}
