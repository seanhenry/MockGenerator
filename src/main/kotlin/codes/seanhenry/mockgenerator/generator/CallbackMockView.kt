package codes.seanhenry.mockgenerator.generator

class CallbackMockView(private val callback: (MockViewModel) -> String): MockView {

  var result = listOf<String>()

  override fun render(model: MockViewModel) {
    result = callback(model)
        .split("\n")
        .filter { it.isNotBlank() }
        .map { it.trim() }
  }
}
