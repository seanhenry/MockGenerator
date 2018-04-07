package codes.seanhenry.mockgenerator.visitor

import codes.seanhenry.mockgenerator.ast.*

open class RecursiveVisitor: Visitor() {

  override fun visitFunctionType(type: FunctionType) {
    type.parameters.forEach { it.accept(this) }
    type.returnType.accept(this)
    super.visitFunctionType(type)
  }

  override fun visitOptionalType(type: OptionalType) {
    type.type.accept(this)
    super.visitOptionalType(type)
  }

  override fun visitBracketType(type: BracketType) {
    type.type.accept(this)
    super.visitBracketType(type)
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
}
