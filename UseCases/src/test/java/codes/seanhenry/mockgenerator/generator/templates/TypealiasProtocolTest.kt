package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class TypealiasProtocolTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method("typealiasClosure", null, listOf(Parameter("closure", "closure", "Completion", "(Int) -> (String)", "closure: Completion")), "func typealiasClosure(closure: Completion)"),
        Method("internalTypealiasClosure", null, listOf(Parameter("closure", "closure", "T", "(String) -> ()", "closure: ClosureProtocol.T")), "func internalTypealiasClosure(closure: T)")
    )
  }

  override fun getExpected(): String {
    return """
    var invokedTypealiasClosure = false
    var invokedTypealiasClosureCount = 0
    var stubbedTypealiasClosureClosureResult: (Int, Void)?
    func typealiasClosure(closure: Completion) {
    invokedTypealiasClosure = true
    invokedTypealiasClosureCount += 1
    if let result = stubbedTypealiasClosureClosureResult {
    _ = closure(result.0)
    }
    }
    var invokedInternalTypealiasClosure = false
    var invokedInternalTypealiasClosureCount = 0
    var stubbedInternalTypealiasClosureClosureResult: (String, Void)?
    func internalTypealiasClosure(closure: T) {
    invokedInternalTypealiasClosure = true
    invokedInternalTypealiasClosureCount += 1
    if let result = stubbedInternalTypealiasClosureClosureResult {
    closure(result.0)
    }
    }
    """.trimIndent()
  }
}
