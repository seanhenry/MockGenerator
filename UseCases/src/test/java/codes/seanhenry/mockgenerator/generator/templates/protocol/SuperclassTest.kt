package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.Class
import codes.seanhenry.mockgenerator.generator.Generator

class SuperclassTest : GeneratorTestTemplate {

  override fun build(generator: Generator) {
    generator.add(
        Class.Builder()
            .superclass {
              it.initializer {
                it.parameter("a1") { it.type("Int") }
                    .parameter("a2") { it.type("Int") }
              }
                  .property("a") { it.type("Int").readonly() }
                  .method("methodA") { }
                  .superclass {
                    it.initializer { it.parameter("b") { it.type("Int") } }
                        .property("b") { it.type("Int").readonly() }
                        .method("methodB") { }
                  }
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(b: 0)
      }
      var invokedAGetter = false
      var invokedAGetterCount = 0
      var stubbedA: Int! = 0
      override var a: Int {
      invokedAGetter = true
      invokedAGetterCount += 1
      return stubbedA
      }
      var invokedBGetter = false
      var invokedBGetterCount = 0
      var stubbedB: Int! = 0
      override var b: Int {
      invokedBGetter = true
      invokedBGetterCount += 1
      return stubbedB
      }
      var invokedMethodA = false
      var invokedMethodACount = 0
      override func methodA() {
      invokedMethodA = true
      invokedMethodACount += 1
      }
      var invokedMethodB = false
      var invokedMethodBCount = 0
      override func methodB() {
      invokedMethodB = true
      invokedMethodBCount += 1
      }
      """.trimIndent()
  }
}
