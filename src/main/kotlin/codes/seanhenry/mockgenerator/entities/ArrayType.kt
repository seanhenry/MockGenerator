package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class ArrayType(val type: Type, var useVerboseSyntax: Boolean): Type {

  override val text: String
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

  fun deepCopy(): ArrayType {
    return copy(type = CopyVisitor.copy(type))
  }

  class Builder {

    private var type: Type = TypeIdentifier.EMPTY
    private var useVerboseSyntax = false

    fun type(type: String): Builder {
      this.type = TypeIdentifier(type)
      return this
    }

    fun type(): TypeFactory<Builder> {
      return TypeFactory(this) { this.type = it }
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
