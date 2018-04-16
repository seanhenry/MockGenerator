package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class DictionaryType(var keyType: Type, val valueType: Type, private val useVerboseSyntax: Boolean): Type {

  override val text: String
    get() { return generateText() }

  private fun generateText(): String {
    val key = keyType.text
    val value = valueType.text
    return if (useVerboseSyntax) {
      "Dictionary<$key, $value>"
    } else {
      "[$key: $value]"
    }
  }

  override fun accept(visitor: Visitor) {
    visitor.visitDictionaryType(this)
  }

  fun deepCopy(): DictionaryType {
    return copy(keyType = CopyVisitor.copy(keyType), valueType = CopyVisitor.copy(valueType))
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
