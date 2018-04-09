package codes.seanhenry.mockgenerator.entities

class Protocol(val methods: List<Method>) {

  class Builder {

    private val methods = mutableListOf<Method>()

    fun method(name: String, build: (Method.Builder) -> Unit): Builder {
      val builder = Method.Builder(name)
      build(builder)
      methods.add(builder.build())
      return this
    }

    fun build(): Protocol {
      return Protocol(methods)
    }
  }
}
