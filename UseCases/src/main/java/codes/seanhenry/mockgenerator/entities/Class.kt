package codes.seanhenry.mockgenerator.entities

class Class(initializers: List<Initializer>, properties: List<Property>, methods: List<Method>, val superclass: Class?, protocols: List<Protocol>, val scope: String?): TypeDeclaration(initializers, properties, methods, protocols) {

  class Builder {

    private val methods = mutableListOf<Method>()
    private val properties = mutableListOf<Property>()
    private val initializers = mutableListOf<Initializer>()
    private val protocols = mutableListOf<Protocol>()
    private var superclass: Class? = null
    private var scope: String? = null

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

    fun superclass(build: (Builder) -> Unit): Builder {
      val builder = Builder()
      build(builder)
      superclass = builder.build()
      return this
    }

    fun protocol(build: (Protocol.Builder) -> Unit): Builder {
      val builder = Protocol.Builder()
      build(builder)
      protocols.add(builder.build())
      return this
    }

    fun scope(scope: String): Builder {
      this.scope = scope
      return this
    }

    fun build(): Class {
      return Class(initializers, properties, methods, superclass, protocols, scope)
    }
  }
}
