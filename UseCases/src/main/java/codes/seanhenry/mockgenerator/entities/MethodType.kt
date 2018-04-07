package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.Type;

// TODO: remove erased type - this will be done by a visitor instead of the client now :)
// TODO: rename to ResolvedType
class MethodType(var originalType: Type, var resolvedType: Type, val erasedType: Type) {

  var isVoid = resolvedType.isVoid

  companion object {
    val IMPLICIT: MethodType by lazy { MethodType(Type(""), Type(""), Type("")) }
  }

  class Builder(var originalType: Type, var resolvedType: Type, var erasedType: Type) {

    constructor(original: String, resolved: String, erased: String): this(Type(original), Type(resolved), Type(erased))
    constructor(type: String): this(Type(type), Type(type), Type(type))

    fun build(): MethodType {
      return MethodType(originalType, resolvedType, erasedType)
    }
  }
}
