package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

class SimpleClassTest: MockGeneratorTestTemplate {

  override fun build(generator: XcodeMockGenerator) {
    generator.addClassMethods(
        ProtocolMethod("method", null, "", "func method()"),
        ProtocolMethod("anotherMethod", null, "", "func anotherMethod()")
    )
  }

  override fun getExpected(): String {
    return """
    var invokedMethod = false
    var invokedMethodCount = 0
    override func method() {
    invokedMethod = true
    invokedMethodCount += 1
    }
    var invokedAnotherMethod = false
    var invokedAnotherMethodCount = 0
    override func anotherMethod() {
    invokedAnotherMethod = true
    invokedAnotherMethodCount += 1
    }
      """.trimIndent()
  }
}
