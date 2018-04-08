package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

// TODO: rename this to IdentifierType
// TODO: replace this with BaseType which provides text property to override
// TODO: do we make this an interface and make all other models data classes?
open class TypeIdentifier(open var text: String) {

  val isVoid: Boolean by lazy { listOf(VOID.text, EMPTY_TUPLE.text, VOID_TUPLE.text).contains(text) }
  val isEmpty: Boolean by lazy { EMPTY.text == text }

  companion object {
    val VOID = TypeIdentifier("Void")
    val EMPTY_TUPLE = TypeIdentifier("()")
    val VOID_TUPLE = TypeIdentifier("(Void)")
    val EMPTY = TypeIdentifier("")
  }

  open fun accept(visitor: Visitor) {
    visitor.visitType(this)
  }

  class Factory<B>(val previousBuilder: B, val getType: (TypeIdentifier) -> Unit) {

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
        getType(BracketType(it))
      }
    }

    fun type(type: String): B {
      getType(TypeIdentifier(type))
      return previousBuilder
    }
  }
}
