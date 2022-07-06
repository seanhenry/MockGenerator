package codes.seanhenry.mockgenerator.entities

class MockClass(val inheritedClass: Class?, protocols: List<Protocol>, val scope: String?): TypeDeclaration(emptyList(), emptyList(), emptyList(), emptyList(), protocols) {

  class Builder {

    private val protocols = mutableListOf<Protocol>()
    private var superclass: Class? = null
    private var scope: String? = null

    fun superclass(build: (Class.Builder) -> Unit): Builder {
      val builder = Class.Builder()
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

    fun build(): MockClass {
      return MockClass(superclass, protocols, scope)
    }
  }
}
