package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

data class TypeIdentifier(var identifiers: MutableList<String>): Type {

  constructor(identifier: String): this(mutableListOf(identifier))

  val isEmpty: Boolean
    get() { return text.isEmpty() }

  val firstIdentifier: String
    get() { return identifiers.first() }

  override val text: String
    get() { return identifiers.joinToString(".") }

  override fun accept(visitor: Visitor) {
    visitor.visitTypeIdentifier(this)
  }

  companion object {
    fun isVoid(type: Type): Boolean {
      return listOf(VOID.text, EMPTY_TUPLE.text, VOID_TUPLE.text).contains(type.text)
    }

    fun isEmpty(type: Type): Boolean {
      return EMPTY.text == type.text
    }

    val VOID = TypeIdentifier("Void")
    val EMPTY_TUPLE = TupleType.Builder().build()
    val VOID_TUPLE = TupleType.Builder().element(VOID).build()
    val EMPTY = TypeIdentifier("")
    val INT = TypeIdentifier("Int")
  }

  class Builder(identifier: String) {

    private val identifiers = arrayListOf(identifier)

    fun nest(identifier: String): Builder {
      identifiers.add(identifier)
      return this
    }

    fun build(): TypeIdentifier {
      return TypeIdentifier(identifiers)
    }
  }

}
