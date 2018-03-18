package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals

class AppendStringDecoratorTests : TestCase() {

  fun testShouldAppendSuffixToString() {
    assertSuffixCreatesStringFromString("WasCalled", "nameWasCalled", "name")
  }

  fun testShouldReturnEmptyString_whenInputIsEmpty() {
    assertSuffixCreatesStringFromString("invoked", "", "")
  }

  fun testShouldReturnInput_whenSuffixIsEmpty() {
    assertSuffixCreatesStringFromString("", "name", "name")
  }

  fun testShouldHandle1LetterName() {
    assertSuffixCreatesStringFromString("WasCalled", "aWasCalled", "a")
  }

  private fun assertSuffixCreatesStringFromString(suffix: String, expected: String, initial: String) {
    val actual = AppendStringDecorator(null, suffix).decorate(initial)
    assertEquals(expected, actual)
  }
}
