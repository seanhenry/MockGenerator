package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration

class SwiftStringTupleArrayAppender {

  fun transform(property: PropertyDeclaration, value: String): String {
    return property.name + ".append($value)"
  }
}
