package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class TupleType(val tupleElements: List<TupleElement>): Type {

  val labels: List<String?>
    get() { return tupleElements.map { it.label} }
  val types: List<Type>
    get() { return tupleElements.map { it.type} }

  data class TupleElement(val label: String?, val type: Type) {

    val text: String
      get() { return label?.plus(": ").orEmpty() + type.text}

    fun deepCopy(): TupleElement {
      var labelCopy: String? = null
      if (label != null) {
        labelCopy = String(label.toByteArray())
      }
      return TupleElement(labelCopy, CopyVisitor.copy(type))
    }
  }

  override val text: String
    get() { return "(${tupleElements.joinToString { it.text }})" }

  override fun accept(visitor: Visitor) {
    visitor.visitTupleType(this)
  }

  fun deepCopy(): TupleType {
    return copy(tupleElements = tupleElements.map { it.deepCopy() })
  }

  class Builder {

    private val elements = arrayListOf<TupleElement>()

    fun element(identifier: String): Builder {
      return labelledElement(null, identifier)
    }

    fun labelledElement(label: String?, identifier: String): Builder {
      elements.add(TupleElement(label, TypeIdentifier(identifier)))
      return this
    }

    fun element(type: Type): Builder {
      elements.add(TupleElement(null, type))
      return this
    }

    fun element(): TypeFactory<Builder> {
      return labelledElement(null)
    }

    fun labelledElement(label: String?): TypeFactory<Builder> {
      return TypeFactory(this) { elements.add(TupleElement(label, it)) }
    }

    fun build(): TupleType {
      return TupleType(elements)
    }
  }
}
