package codes.seanhenry.mockgenerator.xcode.templates

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.xcode.XcodeMockGenerator

class SimpleProtocolTest: MockGeneratorTestTemplate {

  override fun build(generator: XcodeMockGenerator) {
    generator.add(
        ProtocolMethod("simpleMethod", null, "", "func simpleMethod()")
    )
  }

  override fun getExpected(): String {
    return """
    var invokedSimpleMethod = false
    var invokedSimpleMethodCount = 0
    func simpleMethod() {
    invokedSimpleMethod = true
    invokedSimpleMethodCount += 1
    }
    """.trimIndent()
  }
}
