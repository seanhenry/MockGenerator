package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.protocol.DiamondInheritanceTest
import codes.seanhenry.mockgenerator.generator.templates.protocol.GeneratorTestTemplate
import codes.seanhenry.mockgenerator.generator.templates.protocol.MultipleOverloadingProtocolsTest
import codes.seanhenry.mockgenerator.generator.templates.protocol.MultipleProtocolTest
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

  private fun runTest(template: GeneratorTestTemplate) {
    val view = MustacheMockView()
    val generator = Generator(view)
    template.build(generator)
    generator.generate()
    assertEquals(template.getExpected(), view.rendered)
  }
}
