package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Initializer

class SwiftStringProtocolInitialiserDeclaration {

  fun transform(initializer: Initializer): String {
    val parametersString = initializer.parametersList
        .map { it.text }
        .joinToString(", ")
    return "required init($parametersString)"
  }
}
