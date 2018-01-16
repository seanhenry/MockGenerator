package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.InitialiserCall
import codes.seanhenry.mockgenerator.entities.Parameter
import junit.framework.TestCase

class SwiftStringConvenienceInitCallTest : TestCase() {

  fun testShouldReturnEmptyCallWhenNoParameters() {
    val call = InitialiserCall(emptyList(), false)
    assertEquals("self.init()", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParameterHasNoDefaultValue() {
    val call = InitialiserCall(listOf(Parameter("a", "a", "Type", "a: Type")), false)
    assertEquals("self.init(a: <#a#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParametersHaveNoDefaultValues() {
    val call = InitialiserCall(listOf(
        Parameter("a", "a", "Type", "a: Type"),
        Parameter("b", "b", "Type", "b: Type")
    ), false)
    assertEquals("self.init(a: <#a#>, b: <#b#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValues() {
    val call = InitialiserCall(listOf(
        Parameter("a", "a", "String", "a: String"),
        Parameter("b", "b", "Int", "b: Int")
    ), false)
    assertEquals("self.init(a: \"\", b: 0)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValuesForOptionals() {
    val call = InitialiserCall(listOf(
        Parameter("a", "a", "String?", "a: String?"),
        Parameter("b", "b", "Int?", "a: Int?"),
        Parameter("c", "c", "Object?", "c: Object?")
    ), false)
    assertEquals("self.init(a: nil, b: nil, c: nil)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithWildcard() {
    val call = InitialiserCall(listOf(
        Parameter("_", "a", "String?", "a: String?")
    ), false)
    assertEquals("self.init(nil)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldForceUnrwapCallWithOptionalInitialiser() {
    val call = InitialiserCall(listOf(
        Parameter("a", "a", "String?", "a: String?")
    ), true)
    assertEquals("self.init(a: nil)!", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnOverriddenInitWhenEmptyFailableInitialiser() {
    val call = InitialiserCall(emptyList(), true)
    assertEquals("super.init()!", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldCallWithTryWhenInitialiserThrows() {
    val call = InitialiserCall(emptyList(), false, true)
    assertEquals("try! self.init()", SwiftStringConvenienceInitCall().transform(call))
  }
}
