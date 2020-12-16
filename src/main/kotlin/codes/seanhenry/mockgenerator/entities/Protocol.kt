package codes.seanhenry.mockgenerator.entities

class Protocol(initializers: List<Initializer>, properties: List<Property>, methods: List<Method>, protocols: List<Protocol>): TypeDeclaration(initializers, properties, methods, protocols) {

  class Builder {

    private val methods = mutableListOf<Method>()
    private val properties = mutableListOf<Property>()
    private val initializers = mutableListOf<Initializer>()
    private val protocols = mutableListOf<Protocol>()

    fun initializer(build: (Initializer.Builder) -> Unit): Builder {
      val builder = Initializer.Builder()
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

    fun protocol(build: (Protocol.Builder) -> Unit): Builder {
      val builder = Protocol.Builder()
      build(builder)
      protocols.add(builder.build())
      return this
    }

    fun build(): Protocol {
      return Protocol(initializers, properties, methods, protocols)
    }
  }
}
