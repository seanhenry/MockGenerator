package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*

class VisitorSpy: Visitor() {

  var didVisitType = false
  override fun visitType(type: Type) {
    super.visitType(type)
    didVisitType = true
  }

  var didVisitTypeIdentifier = false
  override fun visitTypeIdentifier(type: TypeIdentifier) {
    super.visitTypeIdentifier(type)
    didVisitTypeIdentifier = true
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

  var didVisitTupleType = false
  override fun visitTupleType(type: TupleType) {
    super.visitTupleType(type)
    didVisitTupleType = true
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

  var didVisitMethod = false
  override fun visitMethod(declaration: Method) {
    super.visitMethod(declaration)
    didVisitMethod = true
  }

  var didVisitProperty = false
  override fun visitProperty(declaration: Property) {
    super.visitProperty(declaration)
    didVisitProperty = true
  }

  var didVisitInitializer = false
  override fun visitInitializer(declaration: Initializer) {
    super.visitInitializer(declaration)
    didVisitInitializer = true
  }

  var didVisitParameter = false
  override fun visitParameter(parameter: Parameter) {
    super.visitParameter(parameter)
    didVisitParameter = true
  }
}
