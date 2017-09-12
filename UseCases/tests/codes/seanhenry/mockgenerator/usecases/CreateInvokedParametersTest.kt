package codes.seanhenry.mockgenerator.usecases

import junit.framework.TestCase

class CreateInvokedParametersTest: TestCase() {

  fun testShouldTransformName() {
    val property = transformName("name")
    assertEquals("invokedNameParameters", property?.name)
  }

  fun testShouldTransformLongName() {
    val property = transformName("longName")
    assertEquals("invokedLongNameParameters", property?.name)
  }

  fun testShouldReturnNil_whenNoParameters() {
    assertNull(transformParameters(""))
  }

  fun testShouldReturnTupleWithVoid_whenOneParameter() {
    val property = transformParameters("param1: Int")
    assertEquals("(param1: Int, Void)", property?.type)
  }

  fun testShouldReturnTuple_whenTwoParameters() {
    val property = transformParameters("param1: Int, param2: String")
    assertEquals("(param1: Int, param2: String)", property?.type)
  }

  fun testShouldReturnTuple_whenMoreThanTwoParameters() {
    val property = transformParameters("param1: Int, param2: String, param3: UInt")
    assertEquals("(param1: Int, param2: String, param3: UInt)", property?.type)
  }

  fun testShouldReturnUseParameterName_whenParameterLabelIsPresent() {
    val property = transformParameters("label1 name1: Int")
    assertEquals("(name1: Int, Void)", property?.type)
  }

  fun testShouldReturnUseParameterNames_whenParameterLabelsArePresent() {
    val property = transformParameters("label1 name1: Int, label2 name2: String")
    assertEquals("(name1: Int, name2: String)", property?.type)
  }

  fun testShouldReturnNull_whenParameterIsMissingAType() {
    val property = transformParameters("label1 name1, label2 name2: String")
    assertNull(property)
  }

  fun testShouldReturnNull_whenParameterIsMissingALabel() {
    val property = transformParameters(":String, label2 name2: String")
    assertNull(property)
  }

  fun testShouldHandleWhitespace() {
    val property = transformParameters("  label1  name1 : Int ,  label2  name2 : String ")
    assertEquals("(name1: Int, name2: String)", property?.type)
  }

  fun testShouldHandleNewlines() {
    val property = transformParameters("""
        label1
        name1
        :
        Int
        ,
        label2  name2
        : String
        """.trimIndent())
    assertEquals("(name1: Int, name2: String)", property?.type)
  }

  fun testShouldHandleTabs() {
    val property = transformParameters("\n\tlabel1\n\tname1\n\t:\tInt")
    assertEquals("(name1: Int, Void)", property?.type)
  }

  fun testShouldIgnoreClosureParameter() {
    val property = transformParameters("param1: (arg: String) -> Void, param2: String")
    assertEquals("(param2: String, Void)", property?.type)
  }

  private fun transformParameters(parameters: String) = CreateInvokedParameters().transform("name", parameters)

  private fun transformName(name: String) = CreateInvokedParameters().transform(name, "param1: String")
}