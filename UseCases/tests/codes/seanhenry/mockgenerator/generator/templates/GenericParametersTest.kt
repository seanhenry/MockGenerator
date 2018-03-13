package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.GenericType
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.generator.MockGenerator

class GenericParametersTest: MockGeneratorTestTemplate {

  override fun build(generator: MockGenerator) {
    generator.add(
        ProtocolMethod("generic", null, listOf(Parameter("a", "a", "Any", Type("T"), "a: T")), "func generic<T>(a: T)"),
        ProtocolMethod("generic", null, listOf(Parameter("array", "array", "[Any]", Type("[T]"), "a: [T]")), "func generic<T>(array: [T])"),
        ProtocolMethod("generic", null, listOf(Parameter("b", "b", "Any", Type("T"), "b: T")), "func generic<T: NSObject>(b: T.Type)"),
        ProtocolMethod("generic", null, listOf(
            Parameter("c", "c", "Any?", Type("T?"), "c: T?"),
            Parameter("d", "d", "Any!", Type("U!"), "c: U!")
        ), "func generic<T, U>(c: T?, d: U!)")
    )
  }

  override fun getExpected(): String {
    return """
      var invokedGenericA = false
      var invokedGenericACount = 0
      var invokedGenericAParameters: (a: Any, Void)?
      var invokedGenericAParametersList = [(a: Any, Void)]()
      func generic<T>(a: T) {
      invokedGenericA = true
      invokedGenericACount += 1
      invokedGenericAParameters = (a, ())
      invokedGenericAParametersList.append((a, ()))
      }
      var invokedGenericArray = false
      var invokedGenericArrayCount = 0
      var invokedGenericArrayParameters: (array: [Any], Void)?
      var invokedGenericArrayParametersList = [(array: [Any], Void)]()
      func generic<T>(array: [T]) {
      invokedGenericArray = true
      invokedGenericArrayCount += 1
      invokedGenericArrayParameters = (array, ())
      invokedGenericArrayParametersList.append((array, ()))
      }
      var invokedGenericB = false
      var invokedGenericBCount = 0
      var invokedGenericBParameters: (b: Any, Void)?
      var invokedGenericBParametersList = [(b: Any, Void)]()
      func generic<T: NSObject>(b: T.Type) {
      invokedGenericB = true
      invokedGenericBCount += 1
      invokedGenericBParameters = (b, ())
      invokedGenericBParametersList.append((b, ()))
      }
      var invokedGenericC = false
      var invokedGenericCCount = 0
      var invokedGenericCParameters: (c: Any?, d: Any?)?
      var invokedGenericCParametersList = [(c: Any?, d: Any?)]()
      func generic<T, U>(c: T?, d: U!) {
      invokedGenericC = true
      invokedGenericCCount += 1
      invokedGenericCParameters = (c, d)
      invokedGenericCParametersList.append((c, d))
      }
      """.trimIndent()
  }
}
