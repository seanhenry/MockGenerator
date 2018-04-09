package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer


class ParameterAnnotationsTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("escaping").parameter("closure") { param ->
          param.escaping().type().function { }
        }.build(),
        Method.Builder("inOut").parameter("var1") { param ->
          param.inout().type("Int")
        }.build(),
        Method.Builder("autoclosure").parameter("closure") { param ->
          param.annotation("@autoclosure").type().function { }
        }.build(),
        Method.Builder("convention").parameter("closure") { param ->
          param.annotation("@convention(swift)").type().function { }
        }.build()
    )
  }

  override fun getExpected(): String {
    return """
    var invokedEscaping = false
    var invokedEscapingCount = 0
    func escaping(closure: @escaping () -> ()) {
    invokedEscaping = true
    invokedEscapingCount += 1
    closure()
    }
    var invokedInOut = false
    var invokedInOutCount = 0
    var invokedInOutParameters: (var1: Int, Void)?
    var invokedInOutParametersList = [(var1: Int, Void)]()
    func inOut(var1: inout Int) {
    invokedInOut = true
    invokedInOutCount += 1
    invokedInOutParameters = (var1, ())
    invokedInOutParametersList.append((var1, ()))
    }
    var invokedAutoclosure = false
    var invokedAutoclosureCount = 0
    func autoclosure(closure: @autoclosure () -> ()) {
    invokedAutoclosure = true
    invokedAutoclosureCount += 1
    closure()
    }
    var invokedConvention = false
    var invokedConventionCount = 0
    func convention(closure: @convention(swift) () -> ()) {
    invokedConvention = true
    invokedConventionCount += 1
    closure()
    }
    """.trimIndent()
  }
}
