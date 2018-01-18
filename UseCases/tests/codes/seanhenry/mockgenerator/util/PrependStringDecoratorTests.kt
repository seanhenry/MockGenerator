package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals

class PrependStringDecoratorTests : TestCase() {

  fun testShouldPrependPrefixToString() {
    assertPrefixCreatesStringFromString("invoked", "invokedName", "name")
  }

  fun testShouldReturnEmptyString_whenInputIsEmpty() {
    assertPrefixCreatesStringFromString("invoked", "", "")
  }

  fun testShouldReturnInput_whenPrefixIsEmpty() {
    assertPrefixCreatesStringFromString("", "name", "name")
  }

  fun testShouldHandle1LetterName() {
    assertPrefixCreatesStringFromString("invoked", "invokedA", "a")
  }

  private fun assertPrefixCreatesStringFromString(prefix: String, expected: String, initial: String) {
    val actual = PrependStringDecorator(null, prefix).process(initial)
    assertEquals(expected, actual)
  }
}
