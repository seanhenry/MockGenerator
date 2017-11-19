package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserCall

class SwiftStringInitialiserDeclaration {
  fun transform(call: InitialiserCall): String {
    if (call.parameters.isEmpty()) {
      return "override init()"
    }
    return "convenience init()"
  }

}
