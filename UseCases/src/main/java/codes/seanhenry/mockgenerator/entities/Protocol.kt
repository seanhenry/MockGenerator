package codes.seanhenry.mockgenerator.entities

class Protocol(val initializers: List<Initialiser>, val properties: List<Property>, val methods: List<Method>) {

  class Builder {

    private val methods = mutableListOf<Method>()
    private val properties = mutableListOf<Property>()
    private val initializers = mutableListOf<Initialiser>()

    fun initializer(build: (Initialiser.Builder) -> Unit): Builder {
      val builder = Initialiser.Builder()
      build(builder)
      initializers.add(builder.build())
      return this
    }

    fun property(name: String, build: (Property.Builder) -> Unit): Builder {
      val builder = Property.Builder(name)
      build(builder)
      properties.add(builder.build())
      return this
    }

    fun method(name: String, build: (Method.Builder) -> Unit): Builder {
      val builder = Method.Builder(name)
      build(builder)
      methods.add(builder.build())
      return this
    }

    fun build(): Protocol {
      return Protocol(initializers, properties, methods)
    }
  }
}
