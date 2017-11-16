package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.InitialiserMethod
import codes.seanhenry.mockgenerator.entities.InitialiserMethodCall

class CreateConvenienceInitialiser {

  fun transform(initialiser: InitialiserMethod): InitialiserMethodCall? {
    if (initialiser.parametersList.isEmpty()) {
      return null
    }
    return InitialiserMethodCall(initialiser.parametersList.toList())
  }
}
