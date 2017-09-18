package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class TransformToOptionalTest : TestCase() {

  fun testMakesTypeOptional() {
    assertTransformedEquals("Type?", "Type")
  }

  fun testMakesOtherTypeOptional() {
    assertTransformedEquals("OtherType?", "OtherType")
  }

  fun testKeepsOptional() {
    assertTransformedEquals("Type?", "Type?")
  }

  fun testStripsDoubleOptional() {
    assertTransformedEquals("Type?", "Type??")
  }

  fun testReplacesIUOWithOptional() {
    assertTransformedEquals("Type?", "Type!")
  }

  fun testReplacesDoubleIUOWithOptional() {
    assertTransformedEquals("Type?", "Type!!")
  }

  fun testReplacesOptionalIUOWithOptional() {
    assertTransformedEquals("Type?", "Type?!")
  }

  fun testReplacesIUOOptionalWithOptional() {
    assertTransformedEquals("Type?", "Type!?")
  }

  private fun assertTransformedEquals(expected: String, input: String) {
    val optional = TransformToOptional().transform(input)
    assertEquals(expected, optional)
  }
}
