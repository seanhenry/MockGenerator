package codes.seanhenry.mockgenerator.entities

class Property(val name: String, val type: TypeIdentifier, val isWritable: Boolean, val declarationText: String) {

  // TODO: remove and make builder
  constructor(name: String, type: String, isWritable: Boolean, declarationText: String): this(name, TypeIdentifier(type), isWritable, declarationText)

  fun getTrimmedDeclarationText(): String {
    return declarationText.split("{")[0].trimEnd(' ', '\t', '\n')
  }
}
