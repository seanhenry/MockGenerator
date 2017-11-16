package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class InitialiserMethod(val parametersList: List<Parameter>, val signature: String) {

  constructor(parameters: String, signature: String): this(ParameterUtil.getParameters(parameters), signature)
}
