package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class Initialiser(val parametersList: List<Parameter>) {

  constructor(parameters: String): this(ParameterUtil.getParameters(parameters))
}
