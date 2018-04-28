package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SimpleProtocolTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.add(
        Method.Builder("simpleMethod").build()
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
