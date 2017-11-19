package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class Initialiser(val parametersList: List<Parameter>, val isFailable: Boolean) {

  constructor(parameters: String, isFailable: Boolean): this(ParameterUtil.getParameters(parameters), isFailable)
}
