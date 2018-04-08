package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*

class RecursiveVisitorSpy: RecursiveVisitor() {

  var visitedTypes = ArrayList<TypeIdentifier>()
  override fun visitType(type: TypeIdentifier) {
    visitedTypes.add(type)
    super.visitType(type)
  }

  var visitedFunctionTypes = ArrayList<FunctionType>()
  override fun visitFunctionType(type: FunctionType) {
    visitedFunctionTypes.add(type)
    super.visitFunctionType(type)
  }

  var visitedOptionalTypes = ArrayList<OptionalType>()
  override fun visitOptionalType(type: OptionalType) {
    visitedOptionalTypes.add(type)
    super.visitOptionalType(type)
  }

  var visitedBracketTypes = ArrayList<BracketType>()
  override fun visitBracketType(type: BracketType) {
    visitedBracketTypes.add(type)
    super.visitBracketType(type)
  }

  var visitedArrayTypes = ArrayList<ArrayType>()
  override fun visitArrayType(type: ArrayType) {
    visitedArrayTypes.add(type)
    super.visitArrayType(type)
  }

  var visitedDictionaryTypes = ArrayList<DictionaryType>()
  override fun visitDictionaryType(type: DictionaryType) {
    visitedDictionaryTypes.add(type)
    super.visitDictionaryType(type)
  }

  var visitedGenericTypes = ArrayList<GenericType>()
  override fun visitGenericType(type: GenericType) {
    visitedGenericTypes.add(type)
    super.visitGenericType(type)
  }
}
