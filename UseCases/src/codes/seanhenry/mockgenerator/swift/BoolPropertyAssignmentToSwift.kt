package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration

class BoolPropertyAssignmentToSwift {

  fun transform(property: BoolPropertyDeclaration, value: Boolean): String {
    return property.name + " = " + value.toString()
  }
}
