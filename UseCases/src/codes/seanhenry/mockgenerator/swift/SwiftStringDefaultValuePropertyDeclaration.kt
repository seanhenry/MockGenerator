package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringDefaultValuePropertyDeclaration {

  fun transform(declaration: PropertyDeclaration, value: String?): String {
    var defaultValue = ""
    if (value != null) {
      defaultValue = " = $value"
    }
    return SwiftStringPropertyDeclaration().transform(declaration) + defaultValue
  }
}
