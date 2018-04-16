package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class TupleType(val elements: List<Type>): Type {

  override val text: String
    get() { return "(${elements.joinToString { it.text }})" }

  override fun accept(visitor: Visitor) {
    visitor.visitTupleType(this)
  }

  fun deepCopy(): TupleType {
    return copy(elements = elements.map { CopyVisitor.copy(it) })
  }

  class Builder {

    private val elements = arrayListOf<Type>()

    fun element(identifier: String): Builder {
      elements.add(TypeIdentifier(identifier))
      return this
    }

    fun element(type: Type): Builder {
      elements.add(type)
      return this
    }

    fun element(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) { elements.add(it) }
    }

    fun build(): TupleType {
      return TupleType(elements)
    }
  }
}
