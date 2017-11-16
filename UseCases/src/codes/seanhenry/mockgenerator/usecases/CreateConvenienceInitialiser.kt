package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.InitialiserCall

class CreateConvenienceInitialiser {

  fun transform(initialiser: Initialiser): InitialiserCall? {
    if (initialiser.parametersList.isEmpty()) {
      return null
    }
    return InitialiserCall(initialiser.parametersList.toList())
  }
}
