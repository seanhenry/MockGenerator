package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.generator.Generator

interface GeneratorTestTemplate {
  fun build(generator: Generator)
  fun getExpected(): String
}
