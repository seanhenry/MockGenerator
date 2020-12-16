package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import codes.seanhenry.mockgenerator.util.ParameterUtil
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CreateParameterTupleTest {

  @Test
  fun testShouldReturnNil_whenNoParameters() {
    assertNull(transformParameters(""))
  }

  @Test
  fun testShouldReturnNil_whenOnlyWhitespace() {
    assertNull(transformParameters("     \n  \t   "))
  }

  @Test
  fun testShouldReturnTupleWithVoid_whenOneParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param1: Int, Void)", parameters, "param1: Int")
  }

  @Test
  fun testShouldReturnTuple_whenTwoParameters() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("param2", "String")
    )
    assertTuple("(param1: Int, param2: String)", parameters, "param1: Int, param2: String")
  }

  @Test
  fun testShouldReturnTuple_whenMoreThanTwoParameters() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param1", "Int"),
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("param3", "UInt")
    )
    assertTuple("(param1: Int, param2: String, param3: UInt)", parameters, "param1: Int, param2: String, param3: UInt")
  }

  @Test
  fun testShouldReturnUseParameterName_whenParameterLabelIsPresent() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(name1: Int, Void)", parameters, "label1 name1: Int")
  }

  @Test
  fun testShouldReturnUseParameterNames_whenParameterLabelsArePresent() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("name2", "String")
    )
    assertTuple("(name1: Int, name2: String)", parameters, "label1 name1: Int, label2 name2: String")
  }

  @Test
  fun testShouldHandleWhitespace() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("name1", "Int"),
        TuplePropertyDeclaration.TupleParameter("name2", "String")
    )
    assertTuple("(name1: Int, name2: String)", parameters, "  label1  name1 : Int ,  label2  name2 : String ")
  }

  @Test
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

  @Test
  fun testShouldHandleTabs() {
    assertNull(transformParameters("closure: () -> ()"))
  }

  @Test
  fun testShouldIgnoreClosureOnlyParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param2: String, Void)", parameters, "param1: (arg: String) -> Void, param2: String")
  }

  @Test
  fun testShouldIgnoreClosureParameter() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param2", "String"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param2: String, Void)", parameters, "param1: (arg: String) -> Void, param2: String")
  }

  @Test
  fun testShouldIgnoreClosureTypealiasParameter() {
    val tuple = transformParameters(Parameter.Builder("param", "name")
        .type("Completion")
        .resolvedType().function {}
        .build())
    assertNull(tuple)
  }

  @Test
  fun testShouldReplaceIUOWithOptional() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param0", "String?"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param0: String?, Void)", parameters, "param0: String!")
  }

  @Test
  fun testShouldRemoveInOut() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param0", "Int"),
        TuplePropertyDeclaration.TupleParameter("inout", "Int")
    )
    assertTuple("(param0: Int, inout: Int)", parameters, "param0: inout Int, inout: Int")
  }

  @Test
  fun testShouldReplaceEmptyTupleWithVoid() {
    val parameters = arrayOf(
        TuplePropertyDeclaration.TupleParameter("param0", "Void"),
        TuplePropertyDeclaration.TupleParameter("", "Void")
    )
    assertTuple("(param0: Void, Void)", parameters, "param0: ()")
  }

  private fun transformParameters(parameters: String) = CreateInvokedParameters().transform(ParameterUtil.getParameters(parameters), emptyList())

  private fun transformParameters(vararg parameters: Parameter) = CreateInvokedParameters().transform(listOf(*parameters), emptyList())

  private fun assertTuple(expectedType: String, expectedParameters: Array<TuplePropertyDeclaration.TupleParameter>, methodParameters: String) {
    val property = transformParameters(methodParameters)
    assertEquals(expectedParameters.map { it.type }, property?.parameters?.map { it.type })
    assertEquals(expectedParameters.map { it.name }, property?.parameters?.map { it.name })
    assertEquals(expectedType, property?.text)
  }

  private fun assertTuple(expectedType: String,
                          expectedParameters: Array<TuplePropertyDeclaration.TupleParameter>,
                          parameters: List<Parameter>) {
    val property = CreateInvokedParameters().transform(parameters, emptyList())
    assertEquals(expectedParameters.map { it.type }, property?.parameters?.map { it.type })
    assertEquals(expectedParameters.map { it.name }, property?.parameters?.map { it.name })
    assertEquals(expectedType, property?.text)
  }
}
