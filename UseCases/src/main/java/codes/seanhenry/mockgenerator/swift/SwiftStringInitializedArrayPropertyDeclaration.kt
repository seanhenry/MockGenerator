package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringInitializedArrayPropertyDeclaration {

  fun transform(property: PropertyDeclaration): String {
    return "var " + property.name + " = [" + property.type + "]()"
  }
}
