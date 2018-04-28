package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class GenericType(val identifier: String, val arguments: List<Type>): Type {

  override val text: String
    get() { return generateText() }

  private fun generateText(): String {
    val argumentsList = arguments.joinToString(", ") { it.text }
    return "$identifier<$argumentsList>"
  }

  override fun accept(visitor: Visitor) {
    visitor.visitGenericType(this)
  }

  fun deepCopy(): GenericType {
    return copy(arguments = CopyVisitor.copy(arguments))
  }

  class Builder(private val identifier: String) {

    private val arguments = ArrayList<Type>()

    fun argument(identifier: String): Builder {
      arguments.add(TypeIdentifier(identifier))
      return this
    }

    fun argument(): TypeFactory<Builder> {
      return TypeFactory(this) { arguments.add(it) }
    }

    fun build(): GenericType {
      return GenericType(identifier, arguments)
    }
  }
}
