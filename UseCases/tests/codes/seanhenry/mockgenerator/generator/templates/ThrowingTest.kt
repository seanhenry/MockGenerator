package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.generator.MockGenerator

class ThrowingTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.add(
        ProtocolMethod("throwingMethod", null, emptyList(), "func throwingMethod() throws", true),
        ProtocolMethod("throwingClosure", null, "closure: () throws -> ()", "func throwingClosure(closure: () throws -> ())"),
        ProtocolMethod("throwingClosureArgument", null,"closure: (String) throws -> (String)", "func throwingClosureArgument(closure: (String) throws -> (String))")
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
      var invokedThrowingClosure = false
      var invokedThrowingClosureCount = 0
      func throwingClosure(closure: () throws -> ()) {
      invokedThrowingClosure = true
      invokedThrowingClosureCount += 1
      try? closure()
      }
      var invokedThrowingClosureArgument = false
      var invokedThrowingClosureArgumentCount = 0
      var stubbedThrowingClosureArgumentClosureResult: (String, Void)?
      func throwingClosureArgument(closure: (String) throws -> (String)) {
      invokedThrowingClosureArgument = true
      invokedThrowingClosureArgumentCount += 1
      if let result = stubbedThrowingClosureArgumentClosureResult {
      _ = try? closure(result.0)
      }
      }
      """.trimIndent()
  }
}
