package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

// TODO: remove isProtocol and get that info from visitor
class Initializer(val parametersList: List<Parameter>, val isFailable: Boolean, val throws: Boolean, val isProtocol: Boolean) {

  // TODO: remove these
  constructor(parameters: String, isFailable: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, false, false)
  constructor(parameters: String, isFailable: Boolean, throws: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, throws, false)
  constructor(parameters: String, isFailable: Boolean, throws: Boolean, isProtocol: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, throws, isProtocol)

  class Builder {

    private val parameters = ArrayList<Parameter>()
    private var isFailable = false
    private var throws = false
    private var isProtocol = false

    fun failable(): Builder {
      isFailable = true
      return this
    }

    fun throws(): Builder {
      throws = true
      return this
    }

    fun protocol(): Builder {
      isProtocol = true
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
      return Initializer(parameters, isFailable, throws, isProtocol)
    }
  }
}
