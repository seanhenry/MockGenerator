package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.protocol.*
import junit.framework.TestCase

class GeneratorTest : TestCase() {

  fun testMultipleProtocols() {
    runTest(MultipleProtocolTest())
  }

  fun testDiamondInheritanceProtocols() {
    runTest(DiamondInheritanceTest())
  }

  fun testMultipleOverloadingProtocols() {
    runTest(MultipleOverloadingProtocolsTest())
  }

  fun testPrefersClassOverloadedItems() {
    runTest(ClassAndProtocolTest())
  }

  private fun runTest(template: GeneratorTestTemplate) {
    val view = MustacheMockView()
    val generator = Generator(view)
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(), view.rendered)
  }
}
