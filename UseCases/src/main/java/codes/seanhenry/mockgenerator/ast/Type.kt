package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

open class Type(val text: String) {

  companion object {
    val VOID = Type("Void")
  }

  open fun accept(visitor: Visitor) {
    visitor.visitType(this)
  }
}
