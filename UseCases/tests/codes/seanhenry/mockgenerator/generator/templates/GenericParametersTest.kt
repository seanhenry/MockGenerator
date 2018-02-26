package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.GenericType
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.generator.MockGenerator

class GenericParametersTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.add(
        ProtocolMethod("generic", null, listOf(Parameter("a", "a", "T", GenericType("T"), "a: T")), "func generic<T>(a: T)")
    )
  }

  override fun getExpected(): String {
    return """
      var invokedGeneric = false
      var invokedGenericCount = 0
      var invokedGenericParameters: (a: Any, Void)?
      var invokedGenericParametersList = [(a: Any, Void)]()
      func generic<T>(a: T) {
      invokedGeneric = true
      invokedGenericCount += 1
      invokedGenericParameters = (a, ())
      invokedGenericParametersList.append((a, ()))
      }
      """.trimIndent()
  }
}
