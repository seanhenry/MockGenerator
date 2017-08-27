package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration

class BoolPropertyAssignmentToSwift {

  fun transform(property: BoolPropertyDeclaration): String {
    return property.name + " = " + property.initializer.toString()
  }
}
