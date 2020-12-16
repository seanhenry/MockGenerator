package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.visitor.Visitor

class OptionalizeIUOVisitor : Visitor() {
  companion object {
    fun optionalize(type: Type): Type {
      val visitor = OptionalizeIUOVisitor()
      type.accept(visitor)
      return visitor.transformed ?: type
    }
  }

  private var transformed: OptionalType? = null

  override fun visitOptionalType(type: OptionalType) {
    if (!type.isImplicitlyUnwrapped) {
      return
    }
    transformed = OptionalType.Builder()
        .type(type.type)
        .build()
  }
}