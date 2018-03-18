package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initialiser

class SwiftStringProtocolInitialiserDeclaration {

  fun transform(initialiser: Initialiser): String {
    val parametersString = initialiser.parametersList
        .map { it.text }
        .joinToString(", ")
    return "required init($parametersString)"
  }
}
