package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import codes.seanhenry.mockgenerator.util.ParameterUtil
import junit.framework.TestCase

class CreateParameterTupleTest: TestCase() {

  fun testShouldReturnNil_whenNoParameters() {
    assertNull(transformParameters(""))
  }

  fun testShouldReturnNil_whenOnlyWhitespace() {
    assertNull(transformParameters("     \n  \t   "))
  }

  fun testShouldReturnTupleWithVoid_whenOneParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param1: Int, Void)", parameters, "param1: Int")
  }

  fun testShouldReturnTuple_whenTwoParameters() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("param2", "String")
    )
    assertTuple("(param1: Int, param2: String)", parameters, "param1: Int, param2: String")
  }

  fun testShouldReturnTuple_whenMoreThanTwoParameters() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("param3", "UInt")
    )
    assertTuple("(param1: Int, param2: String, param3: UInt)", parameters, "param1: Int, param2: String, param3: UInt")
  }

  fun testShouldReturnUseParameterName_whenParameterLabelIsPresent() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(name1: Int, Void)", parameters, "label1 name1: Int")
  }

  fun testShouldReturnUseParameterNames_whenParameterLabelsArePresent() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("name2", "String")
    )
    assertTuple("(name1: Int, name2: String)", parameters, "label1 name1: Int, label2 name2: String")
  }

  fun testShouldHandleWhitespace() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("name2", "String")
    )
    assertTuple("(name1: Int, name2: String)", parameters, "  label1  name1 : Int ,  label2  name2 : String ")
  }

  fun testShouldHandleNewlines() {
    val methodParameters = """
        label1
        name1
        :
        Int
        ,
        label2  name2
        : String
        """.trimIndent()
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("name2", "String")
    )
    assertTuple("(name1: Int, name2: String)", parameters, methodParameters)
  }

  fun testShouldHandleTabs() {
    assertNull(transformParameters("closure: () -> ()"))
  }

  fun testShouldIgnoreClosureOnlyParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param2: String, Void)", parameters, "param1: (arg: String) -> Void, param2: String")
  }

  fun testShouldIgnoreClosureParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param2: String, Void)", parameters, "param1: (arg: String) -> Void, param2: String")
  }

  fun testShouldIgnoreClosureTypealiasParameter() {
    val tuple = transformParameters(Parameter("param", "name", "Completion", "() -> ()", "param name: Completion"))
    assertNull(tuple)
  }

  fun testShouldReplaceIUOWithOptional() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param0", "String?"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param0: String?, Void)", parameters, "param0: String!")
  }

  fun testShouldRemoveInOut() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param0", "Int"),
        TuplePropertyDeclaration.TupleParameter("inout", "Int")
    )
    assertTuple("(param0: Int, inout: Int)", parameters, "param0: inout Int, inout: Int")
  }

  private fun transformParameters(parameters: String) = CreateInvokedParameters().transform("name", ParameterUtil.getParameters(parameters), emptyList())

  private fun transformParameters(vararg parameters: Parameter) = CreateInvokedParameters().transform("name", listOf(*parameters), emptyList())

  private fun assertTuple(expectedType: String, expectedParameters: Array<TuplePropertyDeclaration.TupleParameter>, methodParameters: String) {
    val property = transformParameters(methodParameters)
    assertEquals(expectedParameters.map { it.type }, property?.parameters?.map { it.type })
    assertEquals(expectedParameters.map { it.name }, property?.parameters?.map { it.name })
    assertEquals(expectedType, property?.type)
  }

  private fun assertTuple(expectedType: String,
                          expectedParameters: Array<TuplePropertyDeclaration.TupleParameter>,
                          parameters: List<Parameter>) {
    val property = CreateInvokedParameters().transform("name", parameters, emptyList())
    assertEquals(expectedParameters.map { it.type }, property?.parameters?.map { it.type })
    assertEquals(expectedParameters.map { it.name }, property?.parameters?.map { it.name })
    assertEquals(expectedType, property?.type)
  }
}
