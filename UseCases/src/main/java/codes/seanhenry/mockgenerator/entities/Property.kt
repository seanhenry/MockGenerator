package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.Type

class Property(val name: String, val type: Type, val isWritable: Boolean, val declarationText: String) {

  // TODO: remove and make builder
  constructor(name: String, type: String, isWritable: Boolean, declarationText: String): this(name, Type(type), isWritable, declarationText)

  fun getTrimmedDeclarationText(): String {
    return declarationText.split("{")[0].trimEnd(' ', '\t', '\n')
  }
}
