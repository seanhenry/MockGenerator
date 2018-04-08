package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.TypeIdentifier;

// TODO: remove erased type - this will be done by a visitor instead of the client now :)
// TODO: rename to ResolvedType
class MethodType(var originalType: TypeIdentifier, var resolvedType: TypeIdentifier, val erasedType: TypeIdentifier) {

  var isVoid = resolvedType.isVoid

  companion object {
    val IMPLICIT: MethodType by lazy { MethodType(TypeIdentifier(""), TypeIdentifier(""), TypeIdentifier("")) }
  }

  class Builder(private var originalType: TypeIdentifier, private var resolvedType: TypeIdentifier, private var erasedType: TypeIdentifier) {

    constructor(original: String, resolved: String, erased: String): this(TypeIdentifier(original), TypeIdentifier(resolved), TypeIdentifier(erased))
    constructor(type: String): this(TypeIdentifier(type), TypeIdentifier(type), TypeIdentifier(type))

    fun build(): MethodType {
      return MethodType(originalType, resolvedType, erasedType)
    }
  }
}
