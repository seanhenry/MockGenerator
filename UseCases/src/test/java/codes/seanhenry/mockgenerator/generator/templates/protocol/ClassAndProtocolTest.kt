package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.Class
import codes.seanhenry.mockgenerator.generator.Generator

class ClassAndProtocolTest : GeneratorTestTemplate {

  override fun build(generator: Generator) {
    generator.add(
        Class.Builder()
            .superclass {
              it.protocol {
                it.property("sharedProperty") { it.type("Int").readonly() }
                    .method("sharedMethod") {}
              }
                  .initializer { it.parameter("shared") { it.type("Int") } }
                  .property("sharedProperty") { it.type("Int").readonly() }
                  .method("sharedMethod") {}
            }
            .protocol {
              it.property("protocolOnlyProperty") { it.type("Int").readonly() }
              it.method("protocolOnlyMethod") { }
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
    convenience init() {
    self.init(shared: 0)
    }
    var invokedSharedPropertyGetter = false
    var invokedSharedPropertyGetterCount = 0
    var stubbedSharedProperty: Int! = 0
    override var sharedProperty: Int {
    invokedSharedPropertyGetter = true
    invokedSharedPropertyGetterCount += 1
    return stubbedSharedProperty
    }
    var invokedProtocolOnlyPropertyGetter = false
    var invokedProtocolOnlyPropertyGetterCount = 0
    var stubbedProtocolOnlyProperty: Int! = 0
    var protocolOnlyProperty: Int {
    invokedProtocolOnlyPropertyGetter = true
    invokedProtocolOnlyPropertyGetterCount += 1
    return stubbedProtocolOnlyProperty
    }
    var invokedSharedMethod = false
    var invokedSharedMethodCount = 0
    override func sharedMethod() {
    invokedSharedMethod = true
    invokedSharedMethodCount += 1
    }
    var invokedProtocolOnlyMethod = false
    var invokedProtocolOnlyMethodCount = 0
    func protocolOnlyMethod() {
    invokedProtocolOnlyMethod = true
    invokedProtocolOnlyMethodCount += 1
    }
      """.trimIndent()
  }
}

