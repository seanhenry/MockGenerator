package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class SimpleClassTest: MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.addClassProperties(
        Property("property", "String?", true, "var property: String?")
    )
    generator.addClassMethods(
        Method.Builder("method").build(),
        Method.Builder("anotherMethod").build()
    )
  }

  override fun getExpected(): String {
    return """
    var invokedPropertySetter = false
    var invokedPropertySetterCount = 0
    var invokedProperty: String?
    var invokedPropertyList = [String?]()
    var invokedPropertyGetter = false
    var invokedPropertyGetterCount = 0
    var stubbedProperty: String!
    override var property: String? {
    set {
    invokedPropertySetter = true
    invokedPropertySetterCount += 1
    invokedProperty = newValue
    invokedPropertyList.append(newValue)
    }
    get {
    invokedPropertyGetter = true
    invokedPropertyGetterCount += 1
    return stubbedProperty
    }
    }
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
