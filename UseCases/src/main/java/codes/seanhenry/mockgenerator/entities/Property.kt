package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

data class Property(val name: String, val type: Type, val isWritable: Boolean, val declarationText: String): Element {

  override fun accept(visitor: Visitor) {
    visitor.visitProperty(this)
  }

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

    fun type(): TypeFactory<Builder> {
      return TypeFactory(this) { this.type = it }
    }

    fun build(): Property {
      return Property(name, type, isWritable, "var $name: ${type.text}")
    }
  }
}
