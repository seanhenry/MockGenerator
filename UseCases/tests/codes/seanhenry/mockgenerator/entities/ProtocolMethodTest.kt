package codes.seanhenry.mockgenerator.entities

import junit.framework.TestCase

class ProtocolMethodTest: TestCase() {

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
        ProtocolMethod("", null, parameters, "").parameterList
    )
  }

  private fun assertEmptyParameterList(parameters: String) {
    assertTrue(ProtocolMethod("", null, parameters, "").parameterList.isEmpty())
  }
}
