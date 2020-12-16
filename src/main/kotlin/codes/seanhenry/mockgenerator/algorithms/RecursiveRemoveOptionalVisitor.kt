package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.visitor.Visitor

class RecursiveRemoveOptionalVisitor : Visitor() {

  companion object {
    fun removeOptional(type: Type): Type {
      val visitor = RecursiveRemoveOptionalVisitor()
      type.accept(visitor)
      return visitor.transformed ?: type
    }
  }

  private var transformed: Type? = null

  override fun visitOptionalType(type: OptionalType) {
    transformed = type.type
    type.type.accept(this)
  }
}
