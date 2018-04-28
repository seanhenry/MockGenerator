package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.FunctionType
import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.visitor.Visitor

class SurroundOptionalVisitor(private val unwrapped: Boolean): Visitor() {

  companion object {
    fun surround(type: Type, unwrapped: Boolean): Type {
      val visitor = SurroundOptionalVisitor(unwrapped)
      type.accept(visitor)
      return visitor.optional
    }
  }

  private var optional: Type = TypeIdentifier.EMPTY

  override fun visitType(type: Type) {
    optional = buildOptional().type(type).build()
  }

  override fun visitFunctionType(type: FunctionType) {
    optional = buildOptional().type().tuple { it.element(type) }.build()
  }

  private fun buildOptional(): OptionalType.Builder {
    val builder = OptionalType.Builder()
    if (unwrapped) {
      builder.unwrapped()
    }
    return builder
  }
}
