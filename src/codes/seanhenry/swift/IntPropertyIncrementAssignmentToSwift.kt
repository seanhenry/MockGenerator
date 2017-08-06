package codes.seanhenry.swift

import codes.seanhenry.entities.IntPropertyDeclaration

class IntPropertyIncrementAssignmentToSwift {

  fun transform(property: IntPropertyDeclaration): String {
    return property.name + " += 1"
  }
}
