package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ThrowingTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.add(
        ProtocolMethod("throwingMethod", null, emptyList(), "func throwingMethod() throws", true)
    )
  }

  override fun getExpected(): String {
    return """
      var invokedThrowingMethod = false
      var invokedThrowingMethodCount = 0
      var stubbedThrowingMethodError: Error?
      func throwingMethod() throws {
      invokedThrowingMethod = true
      invokedThrowingMethodCount += 1
      if let error = stubbedThrowingMethodError {
      throw error
      }
      }
      """.trimIndent()
  }
}
