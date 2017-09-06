package codes.seanhenry.swift

import codes.seanhenry.entities.BoolPropertyDeclaration

class BoolPropertyDeclarationToSwift {

  fun transform(boolPropertyDeclaration: BoolPropertyDeclaration) : String {
    return "var " + boolPropertyDeclaration.name + " = " + boolPropertyDeclaration.initializer.toString()
  }
}
