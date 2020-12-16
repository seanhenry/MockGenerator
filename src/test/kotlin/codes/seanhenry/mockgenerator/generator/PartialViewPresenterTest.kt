package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.ForwardToSuperTest
import org.junit.jupiter.api.Test

class PartialViewPresenterTest: ViewPresenterTest() {
  override fun getType(): String {
    return "partial"
  }

  @Test
  fun testForwardToSuper() {
    runTest(ForwardToSuperTest())
  }
}