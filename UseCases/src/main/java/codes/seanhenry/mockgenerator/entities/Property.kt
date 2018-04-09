package codes.seanhenry.mockgenerator.entities

class Property(val name: String, val type: Type, val isWritable: Boolean, val declarationText: String) {

  // TODO: remove and make builder
  constructor(name: String,
              type: String,
              isWritable: Boolean,
              declarationText: String) : this(name, TypeIdentifier(type), isWritable, declarationText)

  fun getTrimmedDeclarationText(): String {
    return declarationText.split("{")[0].trimEnd(' ', '\t', '\n')
  }

  class Builder(private val name: String) {

    private var type: Type = TypeIdentifier.EMPTY
    private var isWritable = true

    fun readonly(): Builder {
      isWritable = false
      return this
    }

    fun type(identifier: String): Builder {
      type = TypeIdentifier(identifier)
      return this
    }

    fun type(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) { this.type = it }
    }

    fun build(): Property {
      return Property(name, type, isWritable, "var $name: ${type.text}")
    }
  }
}
