package codes.seanhenry.mockgenerator.util

import codes.seanhenry.mockgenerator.entities.Parameter
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MethodModelTests  {

  private lateinit var model: MethodModel

  @Test
  fun testShouldReturnMethodNameAsFirstPreferredName() {
    assertPreferredName(0, 1, "method")
  }

  @Test
  fun testShouldAppendFirstParameterLabelAsSecondPreferredName() {
    assertPreferredName(1, 2, "methodParam0")
  }

  @Test
  fun testShouldAppendSecondParameterLabelAsThirdPreferredName() {
    assertPreferredName(2, 3, "methodParam0Param1")
  }

  @Test
  fun testShouldAppendFirstType_whenLabelsAreAllUsed() {
    assertPreferredName(1, 3, "methodParam0Type0")
  }

  @Test
  fun testShouldAppendSecondType_whenLabelsAreAllUsed() {
    assertPreferredName(2, 5, "methodParam0Type0Param1Type1")
  }

  @Test
  fun testShouldBeNull_whenNoMoreLabelsOrTypes() {
    assertNull(0, 2)
    assertNull(1, 4)
    assertNull(2, 6)
  }

  @Test
  fun testShouldIgnoreSecondLabelWhenFirstIsWildcard() {
    model = MethodModel("method", "_ param0: Type0")
    assertEquals("method", model.nextPreferredName())
    assertEquals("methodType0", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldIgnoreLabelWhenOnlyWildcardLabel() {
    model = MethodModel("method", "_ : Type0")
    assertEquals("method", model.nextPreferredName())
    assertEquals("methodType0", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldIgnoreAllWildcardLabels() {
    model = MethodModel("method", "_ a: Type0", " _ b: Type1")
    assertEquals("method", model.nextPreferredName())
    assertEquals("methodType0", model.nextPreferredName())
    assertEquals("methodType0Type1", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldOnlyIgnoreWildcardLabels() {
    model = MethodModel("method", "_ a: Type0", "param1: Type1")
    assertEquals("method", model.nextPreferredName())
    assertEquals("methodParam1", model.nextPreferredName())
    assertEquals("methodType0Param1", model.nextPreferredName())
    assertEquals("methodType0Param1Type1", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldIgnoreParameterWhenEmptyType() {
    model = MethodModel("method", "param0 :")
    assertEquals("method", model.nextPreferredName())
    assertEquals("methodParam0", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldIgnoreEmptyTypeWithNoColon() {
    model = MethodModel("method", "param0 ")
    assertEquals("method", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldIgnoreParameterWhenNoType() {
    model = MethodModel("method", "label0 name0")
    assertEquals("method", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testShouldCreateIDFromLabelsAndTypes() {
    createModel(3)
    assertEquals("method(param0:Type0,param1:Type1,param2:Type2)", model.id)
  }

  @Test
  fun testShouldCreateIDIgnoreWhitespace() {
    model = MethodModel("method", "   param0   name0  :  Type0   ")
    assertEquals("method(param0:Type0)", model.id)
  }

  @Test
  fun testShouldCreateID_whenNoParams() {
    model = MethodModel("method")
    assertEquals("method()", model.id)
  }

  @Test
  fun testShouldPreserveTuples() {
    model = MethodModel("tuple", "_ tuple: (String, Int)")
    assertEquals("tuple", model.nextPreferredName())
    assertEquals("tupleStringInt", model.nextPreferredName())
    assertNull(model.nextPreferredName())
  }

  @Test
  fun testPeekNextShouldBeEqualToNextPreferredName() {
    createModel(1)
    var peeked = model.peekNextPreferredName() // method
    assertEquals(model.nextPreferredName(), peeked)
    peeked = model.peekNextPreferredName() // methodParam0
    assertEquals(model.nextPreferredName(), peeked)
    peeked = model.peekNextPreferredName() // methodParam0Param1
    assertEquals(model.nextPreferredName(), peeked)
    assertNull(model.peekNextPreferredName())
  }

  private fun assertPreferredName(paramCount: Int, invocationCount: Int, expected: String) {
    createModel(paramCount)
    assertEquals(expected, nextPreferredName(invocationCount))
  }

  private fun assertNull(paramCount: Int, invocationCount: Int) {
    createModel(paramCount)
    assertNull(nextPreferredName(invocationCount))
  }

  private fun createModel(parameterCount: Int) {
    val parameters = (0 until parameterCount).map {
      Parameter.Builder("param$it", "name$it")
        .type("Type$it")
        .build()
    }
    model = MethodModel("method", parameters)
  }

  private fun nextPreferredName(count: Int): String? {
    for (i in 0 until count - 1) {
      model.nextPreferredName()
    }
    return model.nextPreferredName()
  }
}
