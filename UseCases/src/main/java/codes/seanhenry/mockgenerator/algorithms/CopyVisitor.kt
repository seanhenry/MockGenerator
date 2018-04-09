package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.visitor.Visitor

class CopyVisitor: Visitor() {

  lateinit var copy: Type

  companion object {
    fun copy(type: Type): Type {
      val visitor = CopyVisitor()
      type.accept(visitor)
      return visitor.copy
    }

    fun copy(types: List<Type>): List<Type> {
      return types.map { copy(it) }
    }
  }

  override fun visitTypeIdentifier(type: TypeIdentifier) {
    copy = type.copy()
  }

  override fun visitFunctionType(type: FunctionType) {
    copy = type.deepCopy()
  }

  override fun visitOptionalType(type: OptionalType) {
    copy = type.deepCopy()
  }

  override fun visitBracketType(type: BracketType) {
    copy = type.deepCopy()
  }

  override fun visitArrayType(type: ArrayType) {
    copy = type.deepCopy()
  }

  override fun visitDictionaryType(type: DictionaryType) {
    copy = type.deepCopy()
  }

  override fun visitGenericType(type: GenericType) {
    copy = type.deepCopy()
  }
}
