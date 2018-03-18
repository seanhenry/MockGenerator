package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringPropertyAssignment {

  fun transform(property: PropertyDeclaration, value: String): String {
    return property.name + " = $value"
  }
}