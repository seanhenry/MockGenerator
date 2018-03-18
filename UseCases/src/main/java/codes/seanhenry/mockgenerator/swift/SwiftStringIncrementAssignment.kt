package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringIncrementAssignment {

  fun transform(property: PropertyDeclaration): String {
    return property.name + " += 1"
  }
}
