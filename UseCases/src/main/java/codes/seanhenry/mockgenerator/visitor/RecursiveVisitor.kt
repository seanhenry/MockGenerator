package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.entities.*

open class RecursiveVisitor: Visitor() {

  override fun visitFunctionType(type: FunctionType) {
    type.arguments.forEach { it.accept(this) }
    type.returnType.accept(this)
    super.visitFunctionType(type)
  }

  override fun visitOptionalType(type: OptionalType) {
    type.type.accept(this)
    super.visitOptionalType(type)
  }

  override fun visitTupleType(type: TupleType) {
    type.elements.forEach { it.accept(this) }
    super.visitTupleType(type)
  }

  override fun visitArrayType(type: ArrayType) {
    type.type.accept(this)
    super.visitArrayType(type)
  }

  override fun visitDictionaryType(type: DictionaryType) {
    type.keyType.accept(this)
    type.valueType.accept(this)
    super.visitDictionaryType(type)
  }

  override fun visitGenericType(type: GenericType) {
    type.arguments.forEach { it.accept(this) }
    super.visitGenericType(type)
  }

  override fun visitMethod(declaration: Method) {
    declaration.parametersList.forEach { it.accept(this) }
    declaration.returnType.resolvedType.accept(this)
    super.visitMethod(declaration)
  }

  override fun visitProperty(declaration: Property) {
    declaration.type.accept(this)
    super.visitProperty(declaration)
  }

  override fun visitInitializer(declaration: Initializer) {
    declaration.parametersList.forEach { it.accept(this) }
    super.visitInitializer(declaration)
  }

  override fun visitParameter(parameter: Parameter) {
    parameter.type.resolvedType.accept(this)
    super.visitParameter(parameter)
  }
}
