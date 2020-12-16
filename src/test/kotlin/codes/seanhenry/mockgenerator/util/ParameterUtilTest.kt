package codes.seanhenry.mockgenerator.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ParameterUtilTest {

  @Test
  fun testShouldSeparateParameters() {
    assertParameterList(listOf("p: String", "p: Int"), "p: String, p: Int")
  }

  @Test
  fun testShouldSeparateSingleParameter() {
    assertParameterList(listOf("p: String"), "p: String")
  }

  @Test
  fun testShouldReturnEmptyListForNoParameters() {
    assertEmptyParameterList("")
    assertEmptyParameterList("   \t \n   ")
  }

  @Test
  fun testShouldTrimWhitespace() {
    assertParameterList(listOf("p: String", "p: Int"), "p: String\n\t ,   p: Int  \n")
  }

  @Test
  fun testShouldPreserveTuplesAndClosures() {
    assertParameterList(listOf("t: (String, Int)", "c: (Int, Int) -> ()"), "t: (String, Int) \n\t, c: (Int, Int) -> ()")
  }

  @Test
  fun testShouldReturnEmptyParameters() {
    assert(ParameterUtil.getParameters("").isEmpty())
  }

  @Test
  fun testShouldReturnParameter() {
    assertParameter("label",
        "name",
        "Type",
        "label name: Type",
        "label name: Type"
    )
  }

  @Test
  fun testShouldReturnParameterCopyNameWhenNoLabel() {
    assertParameter("name",
        "name",
        "Type",
        "name: Type",
        "name: Type"
    )
  }

  @Test
  fun testShouldNotReturnParameterWithoutType() {
    assertNullParameter("label name")
  }

  @Test
  fun testShouldNotReturnParameterWithoutNameOrLabel() {
    assertNullParameter(": Type")
  }

  @Test
  fun testShouldReturnParameterWithWildcard() {
    assertParameter("_",
        "name",
        "Type",
        "_ name: Type",
        "_ name: Type"
    )
  }

  @Test
  fun testShouldReturnParameterWithWeirdWhitespace() {
    assertParameter("label",
        "name",
        "Type",
        "label\t  \nname    : \n \tType",
        "   \t\t \n  label\t  \nname    : \n \tType\n \t"
    )
  }

  @Test
  fun testShouldReturnParameterWithClosure() {
    assertParameter("label",
        "name",
        "() -> ()",
        "label name: () -> ()",
        "label name: () -> ()"
    )
  }

  @Test
  fun testShouldReturnParameterWithClosureWithArguments() {
    assertParameter("label",
        "name",
        "(String, String) -> (Int)",
        "label name: (String, String) -> (Int)",
        "label name: (String, String) -> (Int)"
    )
  }

  @Test
  fun testShouldReturnParameterWithClosureWithNestedClosures() {
    assertParameter("label",
        "name",
        "((String, String) -> ()) -> (() -> ())",
        "label name: ((String, String) -> ()) -> (() -> ())",
        "label name: ((String, String) -> ()) -> (() -> ())"
    )
  }

  @Test
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

  @Test
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

  @Test
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

  @Test
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
    assertEquals(expectedLabel, parameter.externalName)
    assertEquals(expectedName, parameter.internalName)
    assertEquals(expectedType, parameter.originalTypeText)
    assertEquals(expectedText, parameter.text)
  }

  private fun assertType(expectedType: String, parameters: String, index: Int = 0) {
    val parameter = ParameterUtil.getParameters(parameters)[index]
    assertEquals(expectedType, parameter.originalTypeText)
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
