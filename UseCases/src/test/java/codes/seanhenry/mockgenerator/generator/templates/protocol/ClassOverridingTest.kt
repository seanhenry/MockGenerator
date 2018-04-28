package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.MockClass
import codes.seanhenry.mockgenerator.generator.Generator

class ClassOverridingTest: GeneratorTestTemplate {

  override fun build(generator: Generator) {
    generator.set(
        MockClass.Builder()
            .superclass {
              it.initializer { it.parameter("a") { it.type("Int") } }
                .property("a") { it.type("Int").readonly() }
                .method("methodA") { }
                  .superclass {
                    it.initializer { it.parameter("a") { it.type("Int") } }
                        .property("a") { it.type("Int").readonly() }
                        .method("methodA") { }
                  }
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
      convenience init() {
      self.init(a: 0)
      }
      var invokedAGetter = false
      var invokedAGetterCount = 0
      var stubbedA: Int! = 0
      override var a: Int {
      invokedAGetter = true
      invokedAGetterCount += 1
      return stubbedA
      }
      var invokedMethodA = false
      var invokedMethodACount = 0
      override func methodA() {
      invokedMethodA = true
      invokedMethodACount += 1
      }
      """.trimIndent()
  }
}
