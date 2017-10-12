package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals

class ParameterUtilTest: TestCase() {

  fun testShouldSeparateParameters() {
    assertParameterList(listOf("p: String", "p: Int"), "p: String, p: Int")
  }

  fun testShouldSeparateSingleParameter() {
    assertParameterList(listOf("p: String"), "p: String")
  }

  fun testShouldReturnEmptyListForNoParameters() {
    assertEmptyParameterList("")
    assertEmptyParameterList("   \t \n   ")
  }

  fun testShouldTrimWhitespace() {
    assertParameterList(listOf("p: String", "p: Int"), "p: String\n\t ,   p: Int  \n")
  }

  fun testShouldPreserveTuplesAndClosures() {
    assertParameterList(listOf("t: (String, Int)", "c: (Int, Int) -> ()"), "t: (String, Int) \n\t, c: (Int, Int) -> ()")
  }

  private fun assertParameterList(expected: List<String>, parameters: String) {
    assertEquals(
        expected,
        ParameterUtil.getParameterList(parameters)
    )
  }

  private fun assertEmptyParameterList(parameters: String) {
    assertTrue(ParameterUtil.getParameterList(parameters).isEmpty())
  }
}
