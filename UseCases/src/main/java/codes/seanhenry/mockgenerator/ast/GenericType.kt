package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class GenericType private constructor(val identifier: String, val arguments: List<Type>): Type {

  override val text: String
    get() { return generateText() }

  private fun generateText(): String {
    val argumentsList = arguments.joinToString(", ") { it.text }
    return "$identifier<$argumentsList>"
  }

  override fun accept(visitor: Visitor) {
    visitor.visitGenericType(this)
  }

  class Builder(private val identifier: String) {

    private val arguments = ArrayList<Type>()

    fun argument(identifier: String): Builder {
      arguments.add(TypeIdentifier(identifier))
      return this
    }

    fun argument(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) { arguments.add(it) }
    }

    fun build(): GenericType {
      return GenericType(identifier, arguments)
    }
  }
}
