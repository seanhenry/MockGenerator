package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.InitialiserCall

class CreateConvenienceInitialiser {

  fun transform(initialiser: Initialiser): InitialiserCall? {
    if (emptyInitialserCanBeInferred(initialiser)) {
      return null
    }
    return InitialiserCall(initialiser.parametersList.toList(), initialiser.isFailable)
  }

  private fun emptyInitialserCanBeInferred(initialiser: Initialiser) =
      initialiser.parametersList.isEmpty() && !initialiser.isFailable
}
