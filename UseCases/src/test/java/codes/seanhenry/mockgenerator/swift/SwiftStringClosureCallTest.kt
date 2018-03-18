package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Closure
import junit.framework.TestCase
import kotlin.test.assertEquals

class SwiftStringClosureCallTest: TestCase() {

  fun testShouldCallEmptyClosure() {
    val closure = Closure("closure", emptyList(), "", false)
    assertEquals("closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldCallClosureWithArgument() {
    val closure = Closure("closure", listOf("Type"), "", false)
    val expected = """
    if let result = closureResult {
    closure(result.0)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("closureResult", closure))
  }

  fun testShouldCallClosureWithArguments() {
    val closure = Closure("closure", listOf("Type", "AnotherType", "ThirdType"), "", false)
    val expected = """
    if let result = propertyName {
    closure(result.0, result.1, result.2)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("propertyName", closure))
  }

  fun testShouldSuppressWarningForUnusedClosureReturnValue() {
    val closure = Closure("closure", emptyList(), "String", false)
    assertEquals("_ = closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldSuppressWarningForUnusedClosureReturnValueWhenMultipleArguments() {
    val closure = Closure("closure", listOf("Type", "AnotherType", "ThirdType"), "(String, Int)", false)
    val expected = """
    if let result = propertyName {
    _ = closure(result.0, result.1, result.2)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("propertyName", closure))
  }

  fun testShouldSuppressWarningForTypeAliasedClosure() {
    val closure = Closure("closure", emptyList(), "String", false)
    assertEquals("_ = closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldCallOptionalClosure() {
    val closure = Closure("closure", emptyList(), "String", true)
    assertEquals("_ = closure?()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldCallThrowingClosure() {
    val closure = Closure("closure", emptyList(), "", false, true)
    assertEquals("try? closure()", SwiftStringClosureCall().transform("", closure))
  }

  fun testShouldCallOptionalClosureWhenMultipleArguments() {
    val closure = Closure("closure", listOf("Type", "AnotherType", "ThirdType"), "(String, Int)", true)
    val expected = """
    if let result = propertyName {
    _ = closure?(result.0, result.1, result.2)
    }
      """.trimIndent()
    assertEquals(expected, SwiftStringClosureCall().transform("propertyName", closure))
  }

  fun testShouldCallThrowingOptionalClosureWithReturnValue() {
    val closure = Closure("closure", emptyList(), "String", true, true)
    assertEquals("_ = try? closure?()", SwiftStringClosureCall().transform("", closure))
  }
}
