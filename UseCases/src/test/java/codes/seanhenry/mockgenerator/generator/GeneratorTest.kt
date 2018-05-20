package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.protocol.*
import junit.framework.TestCase

abstract class GeneratorTest : TestCase() {

  abstract fun getType(): String

  fun testMultipleProtocols() {
    runTest(MultipleProtocolTest())
  }

  fun testDiamondInheritanceProtocols() {
    runTest(DiamondInheritanceTest())
  }

  fun testMultipleOverloadingProtocols() {
    runTest(MultipleOverloadingProtocolsTest())
  }

  fun testRemovesDuplicatesFromOverriddenClasses() {
    runTest(ClassOverridingTest())
  }

  fun testMocksSuperclasses() {
    runTest(SuperclassTest())
  }

  fun testDeepProtocolInheritance() {
    runTest(DeepProtocolInheritanceTest())
  }

  private fun runTest(template: GeneratorTestTemplate) {
    val view = MustacheMockView(getType())
    val generator = Generator(view)
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(getType()), view.rendered)
  }
}
