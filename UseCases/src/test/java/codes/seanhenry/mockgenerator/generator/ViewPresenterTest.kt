package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.*
import junit.framework.TestCase

abstract class ViewPresenterTest: TestCase() {

  abstract fun getType(): String

  lateinit var generator: MockViewPresenter
  lateinit var view: MustacheMockView

  override fun setUp() {
    super.setUp()
    view = MustacheMockView(getType())
    generator = MockViewPresenter(view)
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

  fun testNoArgumentOptionalInitialiser() {
    runTest(NoArgumentFailableInitialiserTest())
  }

  fun testNoProtocolInitialiser() {
    runTest(ProtocolInitialiserTest())
  }

  fun testFiltersToSimplestInitialiser() {
    runTest(SimplestClassInitialiserTest())
  }

  fun testOpenInitialiserShouldSetInitToPublic() {
    runTest(OpenInitialiserTest())
  }

  fun testStubsThrowableMethods() {
    runTest(ThrowingTest())
  }

  fun testStubsThrowableInitialisers() {
    runTest(ThrowingInitialiserTest())
  }

  fun testGenericParameters() {
    runTest(GenericParametersTest())
  }

  fun testGenericReturnValue() {
    runTest(GenericReturnValueTest())
  }

  fun testTuple() {
    runTest(TupleTest())
  }

  fun testReadOnlySubscriptProtocol() {
    runTest(ReadOnlySubscriptProtocolTest())
  }

  fun testSubscriptProtocol() {
    runTest(SubscriptProtocolTest())
  }

  fun testArgumentsSubscriptProtocol() {
    runTest(ArgumentsSubscriptProtocolTest())
  }

  fun testOverloadedSubscriptProtocol() {
    runTest(OverloadedSubscriptProtocolTest())
  }

  fun runTest(template: MockGeneratorTestTemplate) {
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(getType()), view.rendered)
  }
}