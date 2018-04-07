package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*

open class Visitor {

  open fun visitType(type: Type) {
  }

  open fun visitFunctionType(type: FunctionType) {
    visitType(type)
  }

  open fun visitOptionalType(type: OptionalType) {
    visitType(type)
  }

  open fun visitBracketType(type: BracketType) {
    visitType(type)
  }

  open fun visitArrayType(type: ArrayType) {
    visitType(type)
  }

  open fun visitDictionaryType(type: DictionaryType) {
    visitType(type)
  }

  open fun visitGenericType(type: GenericType) {
    visitType(type)
  }
}
