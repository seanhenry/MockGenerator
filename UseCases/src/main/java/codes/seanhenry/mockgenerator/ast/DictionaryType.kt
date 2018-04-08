package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class DictionaryType private constructor(val keyType: Type, val valueType: Type, private val useVerboseSyntax: Boolean): Type {

  override var text: String
    set(_) {}
    get() { return generateText() }

  private fun generateText(): String {
    val key = keyType.text
    val value = valueType.text
    if (useVerboseSyntax) {
      return "Dictionary<$key, $value>"
    } else {
      return "[$key: $value]"
    }
  }

  override fun accept(visitor: Visitor) {
    visitor.visitDictionaryType(this)
  }

  class Builder {

    private var keyType: Type = TypeIdentifier.EMPTY
    private var valueType: Type = TypeIdentifier.EMPTY
    private var useVerboseSyntax = false

    fun keyType(type: String): Builder {
      keyType = TypeIdentifier(type)
      return this
    }

    fun keyType(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) { this.keyType = it }
    }

    fun valueType(type: String): Builder {
      valueType = TypeIdentifier(type)
      return this
    }

    fun valueType(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) { this.valueType = it }
    }

    fun verbose(): Builder {
      useVerboseSyntax = true
      return this
    }

    fun build(): DictionaryType {
      return DictionaryType(keyType, valueType, useVerboseSyntax)
    }
  }
}
