package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserCall
import codes.seanhenry.mockgenerator.entities.Parameter
import junit.framework.TestCase

class SwiftStringInitializerDeclarationTest : TestCase() {

  fun testShouldReturnConvenienceInitialiserWhenHasArguments() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("_", "a")
            .type().optional { it.type("String") }
            .build()
    ), false)
    assertEquals("convenience init()", SwiftStringInitialiserDeclaration().transform(call))
  }

  fun testShouldReturnConvenienceInitialiserWhenHasArgumentsAndFailable() {
    val call = InitialiserCall(listOf(
        Parameter.Builder("_", "a")
            .type().optional { it.type("String") }
            .build()
    ), true)
    assertEquals("convenience init()", SwiftStringInitialiserDeclaration().transform(call))
  }

  fun testShouldReturnOverriddenInitialiserWhenEmptyInitialiser() {
    val call = InitialiserCall(emptyList(), false)
    assertEquals("override init()", SwiftStringInitialiserDeclaration().transform(call))
  }

  fun testShouldReturnOverriddenInitialiserWhenEmptyAndFailableInitialiser() {
    val call = InitialiserCall(emptyList(), true)
    assertEquals("override init()", SwiftStringInitialiserDeclaration().transform(call))
  }
}
