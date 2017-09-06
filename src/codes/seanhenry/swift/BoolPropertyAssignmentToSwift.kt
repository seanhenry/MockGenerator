package codes.seanhenry.swift

import codes.seanhenry.entities.BoolPropertyDeclaration

class BoolPropertyAssignmentToSwift {

  fun transform(property: BoolPropertyDeclaration): String {
    return property.name + " = " + property.initializer.toString()
  }
}
