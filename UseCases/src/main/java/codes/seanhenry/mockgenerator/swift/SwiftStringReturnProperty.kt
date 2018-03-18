package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringReturnProperty {

  fun transform(property: PropertyDeclaration): String {
    return "return " + property.name
  }
}
