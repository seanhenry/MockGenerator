package codes.seanhenry.mockgenerator.entities

class TypeFactory<B>(private val previousBuilder: B, private val getType: (Type) -> Unit) {

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

  fun bracket(): TypeFactory<B> {
    return TypeFactory(previousBuilder) {
      getType(TupleType.Builder().element(it).build())
    }
  }

  fun type(type: String): B {
    getType(TypeIdentifier(type))
    return previousBuilder
  }

  fun typeIdentifier(identifier: String, build: (TypeIdentifier.Builder) -> Unit): B {
    val builder = TypeIdentifier.Builder(identifier)
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
