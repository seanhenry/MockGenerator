package codes.seanhenry.mockgenerator.entities

class InitialiserCall(val parameters: List<Parameter>, val isFailable: Boolean, val throws: Boolean) {

  constructor(parameters: List<Parameter>, isFailable: Boolean): this(parameters, isFailable, false)
}
