package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

class Subscript(val returnType: ResolvedType, val parameters: List<Parameter>, val isWritable: Boolean, val declarationText: String): Element {
  override fun accept(visitor: Visitor) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  class Builder(val returnType: ResolvedType) {

    private val parameters = ArrayList<Parameter>()
    private var isWritable = true

    constructor(returnType: Type) : this(ResolvedType(returnType, returnType))

    fun parameter(name: String, build: (Parameter.Builder) -> Unit): Builder {
      return parameter(null, name, build)
    }

    fun parameter(externalName: String?, internalName: String, build: (Parameter.Builder) -> Unit): Builder {
      val builder = Parameter.Builder(externalName, internalName)
      build(builder)
      return parameter(builder.build())
    }

    private fun parameter(parameter: Parameter): Builder {
      parameters.add(parameter)
      return this
    }

    fun readonly(): Builder {
      isWritable = false
      return this
    }

    fun build(): Subscript {
      return Subscript(returnType, parameters, isWritable, getDeclarationText())
    }

    private fun getDeclarationText(): String {
      return "subscript(${getParametersText()}) -> ${returnType.originalType.text}"
    }

    private fun getParametersText(): String {
      return parameters.joinToString(", ") { it.text }
    }
  }
}