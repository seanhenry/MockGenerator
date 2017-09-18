package codes.seanhenry.mockgenerator.usecases

class TransformToOptional {

  fun transform(type: String): String {
    return RemoveOptional.removeOptional(type) + "?"
  }
}

