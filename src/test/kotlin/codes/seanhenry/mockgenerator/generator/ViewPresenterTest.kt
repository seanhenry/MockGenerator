package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

abstract class ViewPresenterTest {

  abstract fun getType(): String

  lateinit var generator: MockViewPresenter
  lateinit var view: MustacheMockView

  @BeforeEach
  fun setUp() {
    view = MustacheMockView(getType())
    generator = MockViewPresenter(view)
  }

  @Test
  fun testShouldReturnEmptyString_whenNothingToMock() {
    assert(generator.generate().isEmpty())
  }

  @Test
  fun testSimpleProtocol() {
    runTest(SimpleProtocolTest())
  }

  @Test
  fun testMethodParameter() {
    runTest(MethodParameterTest())
  }

  @Test
  fun testDefaultValues() {
    runTest(DefaultValuesTest())
  }

  @Test
  fun testOverloadProtocol() {
    runTest(OverloadProtocolTest())
  }

  @Test
  fun testPropertyProtocol() {
    runTest(PropertyProtocolTest())
  }

  @Test
  fun testReturnProtocol() {
    runTest(ReturnProtocolTest())
  }

  @Test
  fun testScopeProtocol() {
    runTest(ScopeProtocolTest())
  }

  @Test
  fun testClosureProtocol() {
    runTest(ClosureProtocolTest())
  }

  @Test
  fun testAnnotations() {
    runTest(ParameterAnnotationsTest())
  }

  @Test
  fun testTypeAlias() {
    runTest(TypealiasProtocolTest())
  }

  @Test
  fun testKeywords() {
    runTest(KeywordsProtocolTest())
  }

  @Test
  fun testSimpleClass() {
    runTest(SimpleClassTest())
  }

  @Test
  fun testInitialiserWithArguments() {
    runTest(ArgumentsInitialiserTest())
  }

  @Test
  fun testOptionalInitialser() {
    runTest(FailableInitialserTest())
  }

  @Test
  fun testNoArgumentOptionalInitialiser() {
    runTest(NoArgumentFailableInitialiserTest())
  }

  @Test
  fun testNoProtocolInitialiser() {
    runTest(ProtocolInitialiserTest())
  }

  @Test
  fun testFiltersToSimplestInitialiser() {
    runTest(SimplestClassInitialiserTest())
  }

  @Test
  fun testOpenInitialiserShouldSetInitToPublic() {
    runTest(OpenInitialiserTest())
  }

  @Test
  fun testStubsThrowableMethods() {
    runTest(ThrowingTest())
  }

  @Test
  fun testStubsThrowableInitialisers() {
    runTest(ThrowingInitialiserTest())
  }

  @Test
  fun testGenericParameters() {
    runTest(GenericParametersTest())
  }

  @Test
  fun testGenericReturnValue() {
    runTest(GenericReturnValueTest())
  }

  @Test
  fun testTuple() {
    runTest(TupleTest())
  }

  fun runTest(template: MockGeneratorTestTemplate) {
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(getType()), view.rendered)
  }
}