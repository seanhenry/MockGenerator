package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

data class TypeIdentifier(var identifiers: MutableList<String>): Type {

  constructor(identifier: String): this(mutableListOf(identifier))

  val isEmpty: Boolean by lazy { EMPTY.text == text }

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
    val EMPTY_TUPLE = TypeIdentifier("()")
    val VOID_TUPLE = TypeIdentifier("(Void)")
    val EMPTY = TypeIdentifier("")
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

  // TODO: move me
  class Factory<B>(val previousBuilder: B, val getType: (Type) -> Unit) {

    fun function(build: (FunctionType.Builder) -> Unit): B {
      val builder = FunctionType.Builder()
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun optional(build: (OptionalType.Builder) -> Unit): B {
      val builder = OptionalType.Builder()
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun array(build: (ArrayType.Builder) -> Unit): B {
      val builder = ArrayType.Builder()
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun dictionary(build: (DictionaryType.Builder) -> Unit): B {
      val builder = DictionaryType.Builder()
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun generic(identifier: String, build: (GenericType.Builder) -> Unit): B {
      val builder = GenericType.Builder(identifier)
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun bracket(): Factory<B> {
      return Factory(previousBuilder) {
        getType(TupleType.Builder().element(it).build())
      }
    }

    fun type(type: String): B {
      getType(TypeIdentifier(type))
      return previousBuilder
    }

    fun typeIdentifier(identifier: String, build: (Builder) -> Unit): B {
      val builder = Builder(identifier)
      build(builder)
      getType(builder.build())
      return previousBuilder
    }

    fun tuple(build: (TupleType.Builder) -> Unit): B {
      val builder = TupleType.Builder()
      build(builder)
      getType(builder.build())
      return previousBuilder
    }
  }
}
