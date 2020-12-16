package codes.seanhenry.mockgenerator.entities

data class ResolvedType(var originalType: Type,
                        var resolvedType: Type) {

  companion object {
    val IMPLICIT: ResolvedType by lazy { ResolvedType(TypeIdentifier(""), TypeIdentifier("")) }
  }

  class Builder(private var originalType: TypeIdentifier,
                private var resolvedType: TypeIdentifier) {

    constructor(type: String): this(TypeIdentifier(type), TypeIdentifier(type))

    fun build(): ResolvedType {
      return ResolvedType(originalType, resolvedType)
    }
  }
}
