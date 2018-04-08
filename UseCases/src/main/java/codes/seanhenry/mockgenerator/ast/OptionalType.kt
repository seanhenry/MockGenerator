package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class OptionalType private constructor(val type: TypeIdentifier, val isImplicitlyUnwrapped: Boolean, val useVerboseSyntax: Boolean, val implicitlyUnwrapped: Boolean): TypeIdentifier("") {

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

    private var type = TypeIdentifier.EMPTY
    private var implicitlyUnwrapped = false
    private var useVerboseSyntax = false

    fun type(type: String): Builder {
      this.type = TypeIdentifier(type)
      return this
    }

    fun type(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) {
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
