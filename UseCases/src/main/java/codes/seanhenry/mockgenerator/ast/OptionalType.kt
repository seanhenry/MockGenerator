package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class OptionalType(text: String): Type(text) {

  override fun accept(visitor: Visitor) {
    visitor.visitOptionalType(this)
  }
}
