package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

open class Type(val text: String) {

  val isVoid: Boolean by lazy { listOf(VOID.text, EMPTY_TUPLE.text, VOID_TUPLE.text).contains(text) }
  val isEmpty: Boolean by lazy { EMPTY.text == text }

  companion object {
    val VOID = Type("Void")
    val EMPTY_TUPLE = Type("()")
    val VOID_TUPLE = Type("(Void)")
    val EMPTY = Type("")
  }

  open fun accept(visitor: Visitor) {
    visitor.visitType(this)
  }

  class Factory<B>(val previousBuilder: B, val getType: (Type) -> Unit) {

    fun function(build: (FunctionType.Builder) -> Unit): B {
      val builder = FunctionType.Builder()
      build(builder)
      getType(builder.build())
      return this.previousBuilder
    }

    fun optional(build: (OptionalType.Builder) -> Unit): B {
      val builder = OptionalType.Builder()
      build(builder)
      getType(builder.build())
      return this.previousBuilder
    }

    fun array(build: (ArrayType.Builder) -> Unit): B {
      val builder = ArrayType.Builder()
      build(builder)
      getType(builder.build())
      return this.previousBuilder
    }
  }
}
