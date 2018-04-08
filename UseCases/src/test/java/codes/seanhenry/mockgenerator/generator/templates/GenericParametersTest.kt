package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.MethodType
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class GenericParametersTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("generic")
            .genericParameter("T")
            .parameter("a") { it.type("T") }
            .build(),
        Method.Builder("generic")
            .genericParameter("T")
            .parameter("array") { it.type().array { it.type("T") } }
            .build(),
        // TODO: support nested types
        Method("generic", null, listOf(Parameter("b", "b", MethodType.Builder("T", "T", "Any").build(), "b: T")), "func generic<T: NSObject>(b: T.Type)"),
        Method.Builder("generic")
            .genericParameter("T")
            .genericParameter("U")
            .parameter("c") { it.type().optional { it.type("T") } }
            .parameter("d") { it.type().optional { it.unwrapped().type("U") } }
            .build()
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
