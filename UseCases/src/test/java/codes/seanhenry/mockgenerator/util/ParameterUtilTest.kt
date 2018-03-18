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

  fun testShouldReturnEmptyParameters() {
    assert(ParameterUtil.getParameters("").isEmpty())
  }

  fun testShouldReturnParameter() {
    assertParameter("label",
        "name",
        "Type",
        "label name: Type",
        "label name: Type"
    )
  }

  fun testShouldReturnParameterCopyNameWhenNoLabel() {
    assertParameter("name",
        "name",
        "Type",
        "name: Type",
        "name: Type"
    )
  }

  fun testShouldNotReturnParameterWithoutType() {
    assertNullParameter("label name")
  }

  fun testShouldNotReturnParameterWithoutNameOrLabel() {
    assertNullParameter(": Type")
  }

  fun testShouldReturnParameterWithWildcard() {
    assertParameter("_",
        "name",
        "Type",
        "_ name: Type",
        "_ name: Type"
    )
  }

  fun testShouldReturnParameterWithWeirdWhitespace() {
    assertParameter("label",
        "name",
        "Type",
        "label\t  \nname    : \n \tType",
        "   \t\t \n  label\t  \nname    : \n \tType\n \t"
    )
  }

  fun testShouldReturnParameterWithClosure() {
    assertParameter("label",
        "name",
        "() -> ()",
        "label name: () -> ()",
        "label name: () -> ()"
    )
  }

  fun testShouldReturnParameterWithClosureWithArguments() {
    assertParameter("label",
        "name",
        "(String, String) -> (Int)",
        "label name: (String, String) -> (Int)",
        "label name: (String, String) -> (Int)"
    )
  }

  fun testShouldReturnParameterWithClosureWithNestedClosures() {
    assertParameter("label",
        "name",
        "((String, String) -> ()) -> (() -> ())",
        "label name: ((String, String) -> ()) -> (() -> ())",
        "label name: ((String, String) -> ()) -> (() -> ())"
    )
  }

  fun testShouldReturnParameterWithClosureWithATSyntax() {
    assertParameter("label",
        "name",
        "() -> ()",
        "label name: @escaping () -> ()",
        "label name: @escaping () -> ()"
    )
    assertParameter("label",
        "name",
        "() -> ()",
        "label name: @convention ( swift ) () -> ()",
        "label name: @convention ( swift ) () -> ()"
    )
  }

  fun testShouldReturnMultipleParameters() {
    assertParameter("label0",
        "name0",
        "Type0",
        "label0 name0: Type0",
        "label0 name0: Type0, label1 name1: Type1",
        0
    )
    assertParameter("label1",
        "name1",
        "Type1",
        "label1 name1: Type1",
        "label0 name0: Type0, label1 name1: Type1",
        1
    )
  }

  fun testShouldIgnoreDefaultValues() {
    assertParameter("label0",
        "name0",
        "Type0",
        "label0 name0: Type0 = \"Something\"",
        "label0 name0: Type0 = \"Something\", label1 name1: Type1 = 0",
        0
    )
    assertParameter("label1",
        "name1",
        "Type1",
        "label1 name1: Type1 = 0",
        "label0 name0: Type0 = \"Something\", label1 name1: Type1 = 0",
        1
    )
  }

  fun testShouldRecogniseTypesWithSpecialCharacters() {
    assertType("[Int]",
        "a: [Int]",
        0
    )
    assertType("Optional<String>",
        "a: Optional<String>   ",
        0
    )
  }

  private fun assertParameter(expectedLabel: String, expectedName: String, expectedType: String, expectedText: String, parameters: String, index: Int = 0) {
    val parameter = ParameterUtil.getParameters(parameters)[index]
    assertEquals(expectedLabel, parameter.label)
    assertEquals(expectedName, parameter.name)
    assertEquals(expectedType, parameter.type)
    assertEquals(expectedText, parameter.text)
  }

  private fun assertType(expectedType: String, parameters: String, index: Int = 0) {
    val parameter = ParameterUtil.getParameters(parameters)[index]
    assertEquals(expectedType, parameter.type)
  }

  private fun assertNullParameter(parameters: String) {
    assert(ParameterUtil.getParameters(parameters).isEmpty())
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
