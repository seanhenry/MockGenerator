package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.OptionalUtil

class TransformToOptional {

  fun transform(type: String): String {
    return OptionalUtil.removeOptional(type) + "?"
  }
}

