package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class OptionalType(text: String, val type: Type, val isImplicitlyUnwrapped: Boolean): Type(text) {

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
      val text = surroundWithOptional(type.text)
      return OptionalType(text, type, implicitlyUnwrapped)
    }

    private fun surroundWithOptional(text: String): String {
      if (useVerboseSyntax) {
        return surroundWithVerboseOptional(text)
      } else if(implicitlyUnwrapped) {
        return "$text!"
      } else {
        return "$text?"
      }
    }

    private fun surroundWithVerboseOptional(text: String): String {
      return "Optional<$text>"
    }
  }
}
