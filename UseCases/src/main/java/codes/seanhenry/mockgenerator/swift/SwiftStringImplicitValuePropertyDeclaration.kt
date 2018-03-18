package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringImplicitValuePropertyDeclaration {

  fun transform(property: PropertyDeclaration, value: String): String {
    return "var " + property.name + " = " + value
  }
}
