package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringPropertyDeclaration {

  fun transform(declaration: PropertyDeclaration): String {
    return "var " + declaration.name + ": " + declaration.type
  }
}
