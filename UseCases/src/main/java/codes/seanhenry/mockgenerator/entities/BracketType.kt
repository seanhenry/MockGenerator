package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

data class BracketType(val type: Type): Type {

  override val text: String
    get() { return "(${type.text})" }

  override fun accept(visitor: Visitor) {
    visitor.visitBracketType(this)
  }

  fun deepCopy(): BracketType {
    return copy(type = CopyVisitor.copy(type))
  }
}
