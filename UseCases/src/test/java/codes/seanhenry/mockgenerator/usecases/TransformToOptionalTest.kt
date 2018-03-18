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

  fun testPreservesDoubleOptional() {
    assertTransformedEquals("Type??", "Type??")
  }

  fun testReplacesIUOWithOptional() {
    assertTransformedEquals("Type?", "Type!")
  }

  fun testReplacesLastInDoubleIUOWithOptional() {
    assertTransformedEquals("Type!?", "Type!!")
  }

  fun testReplacesLastInOptionalIUOWithOptional() {
    assertTransformedEquals("Type??", "Type?!")
  }

  fun testReplacesLastInIUOOptionalWithOptional() {
    assertTransformedEquals("Type!?", "Type!?")
  }

  private fun assertTransformedEquals(expected: String, input: String) {
    val optional = TransformToOptional().transform(input)
    assertEquals(expected, optional)
  }
}
