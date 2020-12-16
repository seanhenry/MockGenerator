package codes.seanhenry.mockgenerator.entities

class Class(initializers: List<Initializer>, properties: List<Property>, methods: List<Method>, val inheritedClass: Class?): TypeDeclaration(initializers, properties, methods, emptyList()) {

  class Builder {

    private val methods = mutableListOf<Method>()
    private val properties = mutableListOf<Property>()
    private val initializers = mutableListOf<Initializer>()
    private var superclass: Class? = null

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

    fun build(): Class {
      return Class(initializers, properties, methods, superclass)
    }
  }
}
