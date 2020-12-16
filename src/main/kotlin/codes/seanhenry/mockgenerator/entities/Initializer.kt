package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

class Initializer(val parametersList: List<Parameter>,
                  val isFailable: Boolean,
                  val throws: Boolean) : Element {

  override fun accept(visitor: Visitor) {
    visitor.visitInitializer(this)
  }

  class Builder {

    private val parameters = ArrayList<Parameter>()
    private var isFailable = false
    private var throws = false

    fun failable(): Builder {
      isFailable = true
      return this
    }

    fun throws(): Builder {
      throws = true
      return this
    }

    fun parameter(name: String, build: (Parameter.Builder) -> Unit): Builder {
      val builder = Parameter.Builder(name)
      build(builder)
      return parameter(builder.build())
    }

    fun parameter(externalName: String, internalName: String, build: (Parameter.Builder) -> Unit): Builder {
      val builder = Parameter.Builder(externalName, internalName)
      build(builder)
      return parameter(builder.build())
    }

    private fun parameter(parameter: Parameter): Builder {
      parameters.add(parameter)
      return this
    }

    fun build(): Initializer {
      return Initializer(parameters, isFailable, throws)
    }
  }
}
