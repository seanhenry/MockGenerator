package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.xcode.templates.*
import junit.framework.TestCase

class XcodeMockGeneratorTest: TestCase() {

  lateinit var generator: XcodeMockGenerator

  override fun setUp() {
    super.setUp()
    generator = XcodeMockGenerator()
  }

  fun testShouldReturnEmptyString_whenNothingToMock() {
    assert(generator.generate().isEmpty())
  }

  fun testSimpleProtocol() {
    runTest(SimpleProtocolTest())
  }

  fun testMethodParameter() {
    runTest(MethodParameterTest())
  }

  fun testDefaultValues() {
    runTest(DefaultValuesTest())
  }

  fun testOverloadProtocol() {
    runTest(OverloadProtocolTest())
  }

  fun testPropertyProtocol() {
    runTest(PropertyProtocolTest())
  }

  fun testReturnProtocol() {
    runTest(ReturnProtocolTest())
  }

  fun testScopeProtocol() {
    runTest(ScopeProtocolTest())
  }

  fun testClosureProtocol() {
    runTest(ClosureProtocolTest())
  }

  fun testAnnotations() {
    runTest(ParameterAnnotationsTest())
  }

  fun testTypeAlias() {
    runTest(TypealiasProtocolTest())
  }

  fun testKeywords() {
    runTest(KeywordsProtocolTest())
  }

  fun testSimpleClass() {
    runTest(SimpleClassTest())
  }

  fun testInitialiserWithArguments() {
    runTest(ArgumentsInitialiserTest())
  }

  fun testOptionalInitialser() {
    runTest(FailableInitialserTest())
  }

  private fun runTest(template: MockGeneratorTestTemplate) {
    template.build(generator)
    assertEquals(template.getExpected(), generator.generate())
  }
}
