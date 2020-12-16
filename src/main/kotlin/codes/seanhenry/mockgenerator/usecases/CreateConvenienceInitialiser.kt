package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.mockgenerator.entities.InitialiserCall

class CreateConvenienceInitialiser {

  fun transform(initializer: Initializer): InitialiserCall? {
    if (emptyInitialiserCanBeInferred(initializer)) {
      return null
    }
    return InitialiserCall(initializer.parametersList, initializer.isFailable, initializer.throws)
  }

  private fun emptyInitialiserCanBeInferred(initializer: Initializer) =
      initializer.parametersList.isEmpty() && !initializer.isFailable
}
