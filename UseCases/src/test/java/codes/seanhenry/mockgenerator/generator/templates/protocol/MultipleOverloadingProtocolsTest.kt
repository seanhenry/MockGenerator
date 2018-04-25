package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.Class
import codes.seanhenry.mockgenerator.entities.Protocol
import codes.seanhenry.mockgenerator.generator.Generator

class MultipleOverloadingProtocolsTest : GeneratorTestTemplate {

  override fun build(generator: Generator) {
    generator.add(
        Class.Builder()
            .protocol {
              it.method("inheriting") {}
                  .method("inherited") { it.parameter("overloaded") { it.type("Int") } }
            }
            .protocol {
              it.method("inherited") { it.parameter("method") { it.type("String") } }
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
      var invokedInheriting = false
      var invokedInheritingCount = 0
      func inheriting() {
      invokedInheriting = true
      invokedInheritingCount += 1
      }
      var invokedInheritedOverloaded = false
      var invokedInheritedOverloadedCount = 0
      var invokedInheritedOverloadedParameters: (overloaded: Int, Void)?
      var invokedInheritedOverloadedParametersList = [(overloaded: Int, Void)]()
      func inherited(overloaded: Int) {
      invokedInheritedOverloaded = true
      invokedInheritedOverloadedCount += 1
      invokedInheritedOverloadedParameters = (overloaded, ())
      invokedInheritedOverloadedParametersList.append((overloaded, ()))
      }
      var invokedInheritedMethod = false
      var invokedInheritedMethodCount = 0
      var invokedInheritedMethodParameters: (method: String, Void)?
      var invokedInheritedMethodParametersList = [(method: String, Void)]()
      func inherited(method: String) {
      invokedInheritedMethod = true
      invokedInheritedMethodCount += 1
      invokedInheritedMethodParameters = (method, ())
      invokedInheritedMethodParametersList.append((method, ()))
      }
      """.trimIndent()
  }
}
