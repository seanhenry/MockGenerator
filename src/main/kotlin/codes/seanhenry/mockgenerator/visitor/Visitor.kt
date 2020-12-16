package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*

open class Visitor {

  open fun visitType(type: Type) {
  }

  open fun visitTypeIdentifier(type: TypeIdentifier) {
    visitType(type)
  }

  open fun visitFunctionType(type: FunctionType) {
    visitType(type)
  }

  open fun visitOptionalType(type: OptionalType) {
    visitType(type)
  }

  open fun visitTupleType(type: TupleType) {
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

  open fun visitMethod(declaration: Method) {
  }

  open fun visitProperty(declaration: Property) {
  }

  open fun visitInitializer(declaration: Initializer) {
  }

  open fun visitParameter(parameter: Parameter) {
  }
}
