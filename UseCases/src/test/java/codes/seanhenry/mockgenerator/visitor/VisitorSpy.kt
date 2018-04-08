package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*

class VisitorSpy: Visitor() {

  var didVisitType = false
  override fun visitType(type: TypeIdentifier) {
    super.visitType(type)
    didVisitType = true
  }

  var didVisitFunctionType = false
  override fun visitFunctionType(type: FunctionType) {
    super.visitFunctionType(type)
    didVisitFunctionType = true
  }

  var didVisitOptionalType = false
  override fun visitOptionalType(type: OptionalType) {
    super.visitOptionalType(type)
    didVisitOptionalType = true
  }

  var didVisitBracketType = false
  override fun visitBracketType(type: BracketType) {
    super.visitBracketType(type)
    didVisitBracketType = true
  }

  var didVisitArrayType = false
  override fun visitArrayType(type: ArrayType) {
    super.visitArrayType(type)
    didVisitArrayType = true
  }

  var didVisitDictionaryType = false
  override fun visitDictionaryType(type: DictionaryType) {
    super.visitDictionaryType(type)
    didVisitDictionaryType = true
  }

  var didVisitGenericType = false
  override fun visitGenericType(type: GenericType) {
    super.visitGenericType(type)
    didVisitGenericType = true
  }
}
