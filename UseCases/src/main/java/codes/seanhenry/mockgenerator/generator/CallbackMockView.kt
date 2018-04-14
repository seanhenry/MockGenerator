package codes.seanhenry.mockgenerator.generator

class CallbackMockView(private val callback: (MockViewModel) -> String): MockView {

  var result = ""

  override fun render(model: MockViewModel) {
    result = callback(model)
  }
}
