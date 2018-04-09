package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserCall
import codes.seanhenry.mockgenerator.entities.Parameter
import junit.framework.TestCase

class SwiftStringConvenienceInitCallTest : TestCase() {

  fun testShouldReturnEmptyCallWhenNoParameters() {
    val call = InitialiserCall(emptyList(), false)
    assertEquals("self.init()", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParameterHasNoDefaultValue() {
    val call = InitialiserCall(listOf(Parameter.Builder("a", "a").type("Type").build()), false)
    assertEquals("self.init(a: <#a#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithTemplateWhenParametersHaveNoDefaultValues() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("a", "a").type("Type").build(),
        Parameter.Builder("b", "b").type("Type").build()
    ), false)
    assertEquals("self.init(a: <#a#>, b: <#b#>)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValues() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("a", "a").type("String").build(),
        Parameter.Builder("b", "b").type("Int").build()
    ), false)
    assertEquals("self.init(a: \"\", b: 0)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithDefaultValuesForOptionals() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("a", "a").type().optional { it.type("String") }.build(),
        Parameter.Builder("b", "b").type().optional { it.type("Int") }.build(),
        Parameter.Builder("c", "c").type().optional { it.type("Object") }.build()
    ), false)
    assertEquals("self.init(a: nil, b: nil, c: nil)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldReturnCallWithWildcard() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("_", "a").type().optional { it.type("String") }.build()
    ), false)
    assertEquals("self.init(nil)", SwiftStringConvenienceInitCall().transform(call))
  }

  fun testShouldForceUnrwapCallWithOptionalInitialiser() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("a", "a").type().optional { it.type("String") }.build()
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
