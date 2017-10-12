package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Closure
import junit.framework.TestCase
import kotlin.test.assertEquals

class SwiftStringClosureCallTest: TestCase() {

  fun testShouldCallEmptyClosure() {
    val closure = Closure("closure", emptyList(), "")
    assertEquals("closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldCallClosureWithArgument() {
    val closure = Closure("closure", listOf("Type"), "")
    val expected = """
    if let result = closureResult {
    closure(result.0)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("closureResult", closure))
  }

  fun testShouldCallClosureWithArguments() {
    val closure = Closure("closure", listOf("Type", "AnotherType", "ThirdType"), "")
    val expected = """
    if let result = propertyName {
    closure(result.0, result.1, result.2)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("propertyName", closure))
  }

  fun testShouldSuppressWarningForUnusedClosureReturnValue() {
    val closure = Closure("closure", emptyList(), "String")
    assertEquals("_ = closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldSuppressWarningForUnusedClosureReturnValueWhenMultipleArguments() {
    val closure = Closure("closure", listOf("Type", "AnotherType", "ThirdType"), "(String, Int)")
    val expected = """
    if let result = propertyName {
    _ = closure(result.0, result.1, result.2)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("propertyName", closure))
  }
}
