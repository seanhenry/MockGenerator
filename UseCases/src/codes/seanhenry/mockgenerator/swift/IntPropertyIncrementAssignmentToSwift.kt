package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration

class IntPropertyIncrementAssignmentToSwift {

  fun transform(property: IntPropertyDeclaration): String {
    return property.name + " += 1"
  }
}
