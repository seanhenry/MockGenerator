package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AppendStringDecoratorTests  {

  @Test
  fun testShouldAppendSuffixToString() {
    assertSuffixCreatesStringFromString("WasCalled", "nameWasCalled", "name")
  }

  @Test
  fun testShouldReturnEmptyString_whenInputIsEmpty() {
    assertSuffixCreatesStringFromString("invoked", "", "")
  }

  @Test
  fun testShouldReturnInput_whenSuffixIsEmpty() {
    assertSuffixCreatesStringFromString("", "name", "name")
  }

  @Test
  fun testShouldHandle1LetterName() {
    assertSuffixCreatesStringFromString("WasCalled", "aWasCalled", "a")
  }

  private fun assertSuffixCreatesStringFromString(suffix: String, expected: String, initial: String) {
    val actual = AppendStringDecorator(null, suffix).decorate(initial)
    assertEquals(expected, actual)
  }
}
