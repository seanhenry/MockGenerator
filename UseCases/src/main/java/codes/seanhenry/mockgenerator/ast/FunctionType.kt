package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class FunctionType(text: String, val parameters: List<Type>, val returnType: Type, throws: Boolean): Type(text) {

  override fun accept(visitor: Visitor) {
    visitor.visitFunctionType(this)
  }
}
