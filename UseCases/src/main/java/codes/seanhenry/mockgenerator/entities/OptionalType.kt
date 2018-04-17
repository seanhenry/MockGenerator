package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class OptionalType(val type: Type, val isImplicitlyUnwrapped: Boolean, val useVerboseSyntax: Boolean, val implicitlyUnwrapped: Boolean): Type {

  override val text: String
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

  fun deepCopy(): OptionalType {
    return this.copy(type = CopyVisitor.copy(type))
  }

  class Builder {

    private var type: Type = TypeIdentifier.EMPTY
    private var implicitlyUnwrapped = false
    private var useVerboseSyntax = false

    fun type(type: String): Builder {
      this.type = TypeIdentifier(type)
      return this
    }

    fun type(type: Type): Builder {
      this.type = type
      return this
    }

    fun type(): TypeIdentifier.Factory<Builder> {
      return TypeIdentifier.Factory(this) {
        if (it is FunctionType) {
          this.type = TupleType.Builder().element(it).build()
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
