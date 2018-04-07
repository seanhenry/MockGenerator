package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class DictionaryType(text: String, val keyType: Type, val valueType: Type): Type(text) {

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
      return DictionaryType(getText(), keyType, valueType)
    }

    private fun getText(): String {
      val key = keyType.text
      val value = valueType.text
      if (useVerboseSyntax) {
        return "Dictionary<$key, $value>"
      } else {
        return "[$key: $value]"
      }
    }
  }
}
