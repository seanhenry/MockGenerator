package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.entities.MethodType
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.util.ParameterUtil

class Method(val name: String, val returnType: MethodType, val parametersList: List<Parameter>, val declarationText: String, val throws: Boolean) {

  // TODO: remove these
  private constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String, throws: Boolean): this(name, MethodType.Builder(returnType ?: "").build(), parametersList, signature, throws)
  constructor(name: String, returnType: String?, parameters: String, signature: String): this(name, returnType, ParameterUtil.getParameters(parameters), signature, false)
  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String): this(name, returnType, parametersList, signature, false)

  class Builder(val name: String) {

    private var returnType = MethodType.IMPLICIT
    private var throws = false
    internal var parameters = ArrayList<Parameter>()

    fun build(): Method {
      return Method(name, returnType, parameters, getDeclarationText(), throws)
    }

    fun returnType(type: String): Builder {
      returnType = MethodType.Builder(type).build()
      return this
    }

    fun returnType(): Type.Factory<Builder> {
      return Type.Factory(this) { returnType = MethodType(it, it, it) }
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

    private fun getDeclarationText(): String {
      var returnString = ""
      var throwString = ""
      var parametersString = ""
      if (returnType != MethodType.IMPLICIT) {
        returnString = " -> ${returnType.originalType.text}"
      }
      if (throws) {
        throwString = " throws"
      }
      parametersString = parameters.map { it.text }.joinToString(", ")
      return "func $name($parametersString)$throwString$returnString"
    }
  }
}
