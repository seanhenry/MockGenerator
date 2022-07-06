package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.protocol.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

abstract class GeneratorTest  {

  abstract fun getType(): String

  @Test
  fun testMultipleProtocols() {
    runTest(MultipleProtocolTest())
  }

  @Test
  fun testDiamondInheritanceProtocols() {
    runTest(DiamondInheritanceTest())
  }

  @Test
  fun testMultipleOverloadingProtocols() {
    runTest(MultipleOverloadingProtocolsTest())
  }

  @Test
  fun testRemovesDuplicatesFromOverriddenClasses() {
    runTest(ClassOverridingTest())
  }

  @Test
  fun testMocksSuperclasses() {
    runTest(SuperclassTest())
  }

  @Test
  fun testDeepProtocolInheritance() {
    runTest(DeepProtocolInheritanceTest())
  }

  fun testAugmentedClassSubscript() {
    runTest(AugmentedClassSubscriptTest())
  }

  private fun runTest(template: GeneratorTestTemplate) {
    val view = MustacheMockView(getType())
    val generator = Generator(view)
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(getType()), view.rendered)
  }
}
