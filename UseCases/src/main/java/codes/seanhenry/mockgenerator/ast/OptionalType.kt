package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class OptionalType private constructor(val type: Type, val isImplicitlyUnwrapped: Boolean, val useVerboseSyntax: Boolean, val implicitlyUnwrapped: Boolean): Type("") {

  override var text: String
    set(_) {}
    get() { return generateText() }

  private fun generateText(): String {
    val text = type.text
    return when {
      useVerboseSyntax -> "Optional<$text>"
      implicitlyUnwrapped -> "$text!"
      else -> "$text?"
    }
  }

  override fun accept(visitor: Visitor) {
    visitor.visitOptionalType(this)
  }

  class Builder {

    private var type = Type.EMPTY
    private var implicitlyUnwrapped = false
    private var useVerboseSyntax = false

    fun type(type: String): Builder {
      this.type = Type(type)
      return this
    }

    fun type(): Type.Factory<Builder> {
      return Type.Factory(this) {
        if (it is FunctionType) {
          this.type = BracketType(it)
        } else {
          this.type = it
        }
      }
    }

    fun unwrapped(): Builder {
      implicitlyUnwrapped = true
      return this
    }

    fun verbose(): Builder {
      useVerboseSyntax = true
      return this
    }

    fun build(): OptionalType {
      return OptionalType(type, implicitlyUnwrapped, useVerboseSyntax, implicitlyUnwrapped)
    }
  }
}
