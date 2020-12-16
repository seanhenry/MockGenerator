package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PrependStringDecoratorTests  {

  @Test
  fun testShouldPrependPrefixToString() {
    assertPrefixCreatesStringFromString("invoked", "invokedName", "name")
  }

  @Test
  fun testShouldReturnEmptyString_whenInputIsEmpty() {
    assertPrefixCreatesStringFromString("invoked", "", "")
  }

  @Test
  fun testShouldReturnInput_whenPrefixIsEmpty() {
    assertPrefixCreatesStringFromString("", "name", "name")
  }

  @Test
  fun testShouldHandle1LetterName() {
    assertPrefixCreatesStringFromString("invoked", "invokedA", "a")
  }

  private fun assertPrefixCreatesStringFromString(prefix: String, expected: String, initial: String) {
    val actual = PrependStringDecorator(null, prefix).process(initial)
    assertEquals(expected, actual)
  }
}
