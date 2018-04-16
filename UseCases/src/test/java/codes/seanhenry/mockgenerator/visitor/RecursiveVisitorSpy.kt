package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*

class RecursiveVisitorSpy: RecursiveVisitor() {

  var visitedTypes = ArrayList<TypeIdentifier>()
  override fun visitTypeIdentifier(type: TypeIdentifier) {
    visitedTypes.add(type)
    super.visitTypeIdentifier(type)
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

  var visitedTupleTypes = ArrayList<TupleType>()
  override fun visitTupleType(type: TupleType) {
    visitedTupleTypes.add(type)
    super.visitTupleType(type)
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

  var visitedMethods = ArrayList<Method>()
  override fun visitMethod(declaration: Method) {
    visitedMethods.add(declaration)
    super.visitMethod(declaration)
  }

  var visitedProperties = ArrayList<Property>()
  override fun visitProperty(declaration: Property) {
    visitedProperties.add(declaration)
    super.visitProperty(declaration)
  }

  var visitedInitializers = ArrayList<Initializer>()
  override fun visitInitializer(declaration: Initializer) {
    visitedInitializers.add(declaration)
    super.visitInitializer(declaration)
  }

  var visitedParameters = ArrayList<Parameter>()
  override fun visitParameter(parameter: Parameter) {
    visitedParameters.add(parameter)
    super.visitParameter(parameter)
  }
}
