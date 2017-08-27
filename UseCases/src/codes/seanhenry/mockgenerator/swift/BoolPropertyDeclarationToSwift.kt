package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration

class BoolPropertyDeclarationToSwift {

  fun transform(boolPropertyDeclaration: BoolPropertyDeclaration) : String {
    return "var " + boolPropertyDeclaration.name + " = " + boolPropertyDeclaration.initializer.toString()
  }
}
