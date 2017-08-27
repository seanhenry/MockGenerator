package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration

class IntPropertyDeclarationToSwift {

  fun  transform(property: IntPropertyDeclaration): String {
    return "var " + property.name + " = " + property.initializer.toString()
  }
}
