package codes.seanhenry.mockgenerator.util

import junit.framework.TestCase
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UniqueMethodNameGeneratorTests : TestCase() {

  private lateinit var generator: UniqueMethodNameGenerator

  fun test_uniqueMethodName_shouldReturnMethodName() {
    assertEquals(arrayOf(MethodModel("methodName")), arrayOf("methodName"))
  }

  fun test_shouldReturnNull_whenIDDoesNotExist() {
    generator = createGenerator()
    assertNull(generator.getMethodName("1"))
  }

  fun test_uniqueMethodName_shouldReturnMethodName_whenNoOverloadedMethods() {
    assertEquals(arrayOf(
        MethodModel("methodName", "param", "param2")
    ), arrayOf(
        "methodName" // should not add labels when method name is unique
    ))
  }

  fun test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName() {
    assertEquals(arrayOf(
        MethodModel("methodName"),
        MethodModel("anotherMethod"),
        MethodModel("methodName", "param: Type")
    ), arrayOf(
        "methodName",
        "anotherMethod",
        // should add label when method name is overloaded
        // should not use type when label is unique
        "methodNameParam"
    ))
  }

  fun test_duplicateMethodName_shouldAppendFirstParameterLabel_toMethodName_whenGivenMethod_hasMultipleParamLabels() {
    assertEquals(arrayOf(
        MethodModel("animate"),
        MethodModel("animate", "withDuration duration: Type"),
        MethodModel("animate", "withDuration duration: Type", "delay: Type"),
        MethodModel("animate", "withDuration duration: Type", "delay: Type", "easing: Ease")
    ), arrayOf(
        "animate", // should not add anything when there is nothing to add
        "animateWithDuration", // should only use label unless all other labels are identical
        "animateWithDurationDelay", // should only use labels when last label is unique
        "animateWithDurationDelayEasing"
    ))
  }

  fun test_duplicateMethodName_shouldUseTypes_whenLabelsMatch() {
    assertEquals(arrayOf(
        MethodModel("setValue", "_ value: String"),
        MethodModel("setValue", "_ value: Int"),
        MethodModel("set", "object: String", "forKey key: String"),
        MethodModel("set", "object: Int", "forKey key: String"),
        MethodModel("setNumber", "_ number: Float", "at index: Int"),
        MethodModel("setNumber", "_ number: Int", "forKey key: String"),
        MethodModel("setMultiple", "_ number: Int", "for key: Int"),
        MethodModel("setMultiple", "_ number: Int", "for key: String")
    ), arrayOf(
        // should use type when method name and parameters are overloaded
        "setValueString",
        "setValueInt",
        "setObjectStringForKey",
        "setObjectIntForKey",
        "setNumberAt",
        "setNumberForKey",
        "setMultipleIntForInt",
        "setMultipleIntForString"
    ))
  }

  fun test_duplicateMethodName_shouldUseTypes_whenLabelsMatch_andNextParamsMatch() {
    assertEquals(arrayOf(
        MethodModel("setValue", "_ value: String"),
        MethodModel("setValue", "_ value: Int"),
        MethodModel("setValue", "_ value: String", "forKey key: String"),
        MethodModel("setValue", "_ value: Int", "forKey key: String")
    ), arrayOf(
        // should use type when labels match and there is another identical method except its type
        "setValueString",
        "setValueInt",
        "setValueStringForKey",
        "setValueIntForKey"
    ))
  }

  fun test_shouldIgnoreDefaultArguments() {
    assertEquals(arrayOf(
        MethodModel("method", "param: String = \"\""),
        MethodModel("method", "param: Int = 345")
    ), arrayOf(
        "methodParamString",
        "methodParamInt"
    ))
  }

  fun test_shouldProcessOneLetterMethodNames() {
    assertEquals(arrayOf(
        MethodModel("a", ""),
        MethodModel("a", "b: Type")
    ), arrayOf(
        "a", "aB"
    ))
  }

  fun test_shouldAllowDuplicateMethods() {
    assertEquals(arrayOf(
        MethodModel("method"),
        MethodModel("method")
    ), arrayOf(
        "method",
        "method"
    ))
  }

  fun test_shouldProcessStrangeWhitespace() {
    assertEquals(arrayOf(
        MethodModel("method", "    param1     :     String   ", " param3   label    : Int  "),
        MethodModel("method", "param1:String", "param2:Int")
    ), arrayOf(
        "methodParam1Param3",
        "methodParam1Param2"
    ))
  }

  fun test_shouldIgnoreIncompleteParameters() {
    assertEquals(arrayOf(
        MethodModel("method", "_"),
        MethodModel("method", "param1"),
        MethodModel("method", ":String"),
        MethodModel("method", ":Int")
    ), arrayOf(
        "method",
        "method",
        "method",
        "method"
    ))
  }

  fun test_shouldIgnoreSpecialCharactersInTuplesAndClosures() {
    assertEquals(arrayOf(
        MethodModel("method", "_ tuple: (String, Int)"),
        MethodModel("method", "_ tuple: (UInt, Float)"),
        MethodModel("anotherMethod", "_ closure: (Int) -> ()"),
        MethodModel("anotherMethod", "_ closure: () -> String ")
    ), arrayOf(
        "methodStringInt",
        "methodUIntFloat",
        "anotherMethodInt",
        "anotherMethodString"
    ))
  }

  private fun createGenerator(vararg models: MethodModel): UniqueMethodNameGenerator {
    generator = UniqueMethodNameGenerator(*models)
    generator.generateMethodNames()
    return generator
  }

  private fun assertEquals(models: Array<MethodModel>, expected: Array<String>) {
    createGenerator(*models)
    for (i in models.indices) {
      assertEquals(expected[i], generator.getMethodName(models[i].id))
    }
  }
}

