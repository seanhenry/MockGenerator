package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.generator.templates.ForwardToSuperTest

class PartialViewPresenterTest: ViewPresenterTest() {
  override fun getType(): String {
    return "partial"
  }

  fun testForwardToSuper() {
    runTest(ForwardToSuperTest())
  }
}