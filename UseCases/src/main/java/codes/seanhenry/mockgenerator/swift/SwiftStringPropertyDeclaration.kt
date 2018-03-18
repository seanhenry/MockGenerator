package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringPropertyDeclaration {

  fun transform(declaration: PropertyDeclaration): String {
    if (declaration == PropertyDeclaration.EMPTY) {
      return ""
    }
    return "var " + declaration.name + ": " + declaration.type
  }
}
