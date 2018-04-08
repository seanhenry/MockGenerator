package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class GenericType(text: String, val identifier: String, val arguments: List<Type>): Type(text) {

  override fun accept(visitor: Visitor) {
    visitor.visitGenericType(this)
  }

  class Builder(private val identifier: String) {

    private val arguments = ArrayList<Type>()

    fun argument(identifier: String): Builder {
      arguments.add(Type(identifier))
      return this
    }

    fun argument(): Type.Factory<Builder> {
      return Type.Factory(this) { arguments.add(it) }
    }

    fun build(): GenericType {
      val argumentsList = arguments.joinToString(", ") { it.text }
      return GenericType("$identifier<$argumentsList>", identifier, arguments)
    }
  }
}
