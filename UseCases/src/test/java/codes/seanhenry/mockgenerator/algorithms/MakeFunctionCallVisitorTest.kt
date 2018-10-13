package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Method
import junit.framework.TestCase

class MakeFunctionCallVisitorTest : TestCase() {
  fun testShouldCallSimpleMethod() {
    assertEquals("method()", make { })
  }

  fun testShouldCallMethodWithLabelledParameter() {
    assertEquals("method(a: a)", make {
      it.parameter("a") { it.type("Int") }
    })
  }

  fun testShouldCallMethodWithWildcardParameter() {
    assertEquals("method(a)", make {
      it.parameter("_", "a") { it.type("Int") }
    })
  }

  fun testShouldCallMethodWithExternalNameParameter() {
    assertEquals("method(a: b)", make {
      it.parameter("a", "b") { it.type("Int") }
    })
  }

  fun testShouldCallMethodWithMultipleParameters() {
    assertEquals("method(a: a, b, c: d)", make {
      it.parameter("a") { it.type("Int") }
          .parameter("_", "b") { it.type("Int") }
          .parameter("c", "d") { it.type("Int") }
    })
  }

  private fun make(build: (Method.Builder) -> Unit): String? {
    val builder = Method.Builder("method")
    build(builder)
    return MakeFunctionCallVisitor.make(builder.build())
  }
}