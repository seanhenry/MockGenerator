package codes.seanhenry.mockgenerator.generator.templates.protocol

import codes.seanhenry.mockgenerator.entities.Class
import codes.seanhenry.mockgenerator.generator.Generator

class DeepProtocolInheritanceTest : GeneratorTestTemplate {

  override fun build(generator: Generator) {
    generator.add(
        Class.Builder()
            .protocol {
              it.method("topMost") {}
                  .protocol {
                    it.method("middle") {}
                        .protocol {
                          it.method("deepest") {}
                        }
                        .protocol {
                          it.method("deepestSibling") {}
                        }
                  }
                  .protocol {
                    it.method("middleSibling1") {}
                        .protocol {
                          it.method("deepestCousin") {}
                        }
                  }
                  .protocol {
                    it.method("middleSibling2") {}
                  }

            }
            .protocol {
              it.method("topSibling") {}
            }
            .build()
    )
  }

  override fun getExpected(): String {
    return """
    var invokedTopMost = false
    var invokedTopMostCount = 0
    func topMost() {
    invokedTopMost = true
    invokedTopMostCount += 1
    }
    var invokedTopSibling = false
    var invokedTopSiblingCount = 0
    func topSibling() {
    invokedTopSibling = true
    invokedTopSiblingCount += 1
    }
    var invokedMiddle = false
    var invokedMiddleCount = 0
    func middle() {
    invokedMiddle = true
    invokedMiddleCount += 1
    }
    var invokedMiddleSibling1 = false
    var invokedMiddleSibling1Count = 0
    func middleSibling1() {
    invokedMiddleSibling1 = true
    invokedMiddleSibling1Count += 1
    }
    var invokedMiddleSibling2 = false
    var invokedMiddleSibling2Count = 0
    func middleSibling2() {
    invokedMiddleSibling2 = true
    invokedMiddleSibling2Count += 1
    }
    var invokedDeepest = false
    var invokedDeepestCount = 0
    func deepest() {
    invokedDeepest = true
    invokedDeepestCount += 1
    }
    var invokedDeepestSibling = false
    var invokedDeepestSiblingCount = 0
    func deepestSibling() {
    invokedDeepestSibling = true
    invokedDeepestSiblingCount += 1
    }
    var invokedDeepestCousin = false
    var invokedDeepestCousinCount = 0
    func deepestCousin() {
    invokedDeepestCousin = true
    invokedDeepestCousinCount += 1
    }
      """.trimIndent()
  }
}
