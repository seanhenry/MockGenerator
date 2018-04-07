package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.ClosureHelper.Companion.createClosure
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ThrowingTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    val empty = emptyList<Parameter>()
    val noReturnType: String? = null
    generator.add(
        Method.Builder("throwingMethod").throws().build(),
        Method.Builder("throwingReturnMethod").returnType("String").throws().build(),
        Method("throwingClosure", noReturnType, createClosure("", "()", true), "func throwingClosure(closure: () throws -> ())"),
        Method("throwingClosureArgument", noReturnType, createClosure("String", "(String)", true), "func throwingClosureArgument(closure: (String) throws -> (String))")
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
      var invokedThrowingReturnMethod = false
      var invokedThrowingReturnMethodCount = 0
      var stubbedThrowingReturnMethodError: Error?
      var stubbedThrowingReturnMethodResult: String! = ""
      func throwingReturnMethod() throws -> String {
      invokedThrowingReturnMethod = true
      invokedThrowingReturnMethodCount += 1
      if let error = stubbedThrowingReturnMethodError {
      throw error
      }
      return stubbedThrowingReturnMethodResult
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
