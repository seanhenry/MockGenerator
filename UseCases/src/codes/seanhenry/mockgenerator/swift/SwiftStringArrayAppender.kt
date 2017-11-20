package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringArrayAppender {

  fun transform(property: PropertyDeclaration, value: String): String {
    return property.name + ".append($value)"
  }
}
