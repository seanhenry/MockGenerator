package codes.seanhenry.swift

import codes.seanhenry.entities.IntPropertyDeclaration

class IntPropertyDeclarationToSwift {

  fun  transform(property: IntPropertyDeclaration): String {
    return "var " + property.name + " = " + property.initializer.toString()
  }
}
