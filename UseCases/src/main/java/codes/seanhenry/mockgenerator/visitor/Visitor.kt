package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.FunctionType
import codes.seanhenry.mockgenerator.ast.OptionalType
import codes.seanhenry.mockgenerator.ast.Type

open class Visitor {

  open fun visitType(type: Type) {

  }

  open fun visitFunctionType(type: FunctionType) {
    visitType(type)
  }

  open fun visitOptionalType(type: OptionalType) {
    visitType(type)
  }
}
