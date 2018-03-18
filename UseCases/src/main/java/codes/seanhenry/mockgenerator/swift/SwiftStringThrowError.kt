package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringThrowError {

  fun transform(property: PropertyDeclaration): String {
    if (property == PropertyDeclaration.EMPTY) {
      return ""
    }
    val name = property.name
    return "if let error = $name {\nthrow error\n}"
  }
}
