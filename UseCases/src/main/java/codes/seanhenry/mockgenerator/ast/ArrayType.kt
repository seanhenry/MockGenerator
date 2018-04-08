package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class ArrayType private constructor(val type: Type, var useVerboseSyntax: Boolean): Type("") {

  override var text: String
    set(_) {}
    get() { return generateText() }

  private fun generateText(): String {
    if (useVerboseSyntax) {
      return "Array<${type.text}>"
    } else {
      return "[${type.text}]"
    }
  }

  override fun accept(visitor: Visitor) {
    visitor.visitArrayType(this)
  }

  class Builder {

    private var type = Type.EMPTY
    private var useVerboseSyntax = false

    fun type(type: String): Builder {
      this.type = Type(type)
      return this
    }

    fun type(): Type.Factory<Builder> {
      return Type.Factory(this) { this.type = it }
    }

    fun verbose(): Builder {
      useVerboseSyntax = true
      return this
    }

    fun build(): ArrayType {
      return ArrayType(type, useVerboseSyntax)
    }
  }
}
