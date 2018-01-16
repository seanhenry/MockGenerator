package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initialiser
import codes.seanhenry.mockgenerator.entities.InitialiserCall

class CreateConvenienceInitialiser {

  fun transform(initialiser: Initialiser): InitialiserCall? {
    if (emptyInitialiserCanBeInferred(initialiser)) {
      return null
    }
    return InitialiserCall(initialiser.parametersList, initialiser.isFailable, initialiser.throws)
  }

  private fun emptyInitialiserCanBeInferred(initialiser: Initialiser) =
      initialiser.parametersList.isEmpty() && !initialiser.isFailable
}
