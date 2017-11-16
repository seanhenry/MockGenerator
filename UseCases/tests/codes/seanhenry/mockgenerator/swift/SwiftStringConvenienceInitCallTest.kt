package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserMethodCall
import codes.seanhenry.mockgenerator.entities.Parameter
import junit.framework.TestCase

class SwiftStringConvenienceInitCallTest: TestCase() {

  fun testShouldReturnEmptyCallWhenNoParameters() {
    val call = InitialiserMethodCall(emptyList())
    assertEquals("self.init()", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParameterHasNoDefaultValue() {
    val call = InitialiserMethodCall(listOf(Parameter("a", "a", "Type", "a: Type")))
    assertEquals("self.init(a: <#a#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParametersHaveNoDefaultValues() {
    val call = InitialiserMethodCall(listOf(
        Parameter("a", "a", "Type", "a: Type"),
        Parameter("b", "b", "Type", "b: Type")
    ))
    assertEquals("self.init(a: <#a#>, b: <#b#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValues() {
    val call = InitialiserMethodCall(listOf(
        Parameter("a", "a", "String", "a: String"),
        Parameter("b", "b", "Int", "b: Int")
    ))
    assertEquals("self.init(a: \"\", b: 0)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValuesForOptionals() {
    val call = InitialiserMethodCall(listOf(
        Parameter("a", "a", "String?", "a: String?"),
        Parameter("b", "b", "Int?", "a: Int?"),
        Parameter("c", "c", "Object?", "c: Object?")
    ))
    assertEquals("self.init(a: nil, b: nil, c: nil)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithWildcard() {
    val call = InitialiserMethodCall(listOf(
        Parameter("_", "a", "String?", "a: String?")
    ))
    assertEquals("self.init(nil)", SwiftStringConvenienceInitCall().transform(call))
  }
}
