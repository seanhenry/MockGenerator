package codes.seanhenry.mockgenerator.ast

class DictionaryType(text: String, val keyType: Type, val valueType: Type): Type(text) {

  class Builder {

    var keyType = Type.EMPTY
    var valueType = Type.EMPTY

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

    fun build(): DictionaryType {
      var space = ""
      if (valueType.text.isNotEmpty()) {
        space = " "
      }
      return DictionaryType("[${keyType.text}:$space${valueType.text}]", keyType, valueType)
    }
  }
}
