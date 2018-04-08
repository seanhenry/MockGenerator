package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class DictionaryType private constructor(val keyType: Type, val valueType: Type, val useVerboseSyntax: Boolean): Type("") {

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

    private var keyType = Type.EMPTY
    private var valueType = Type.EMPTY
    private var useVerboseSyntax = false

    fun keyType(type: String): Builder {
      keyType = Type(type)
      return this
    }

    fun keyType(): Type.Factory<Builder> {
      return Type.Factory(this) { this.keyType = it }
    }

    fun valueType(type: String): Builder {
      valueType = Type(type)
      return this
    }

    fun valueType(): Type.Factory<Builder> {
      return Type.Factory(this) { this.valueType = it }
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
