package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class Initialiser(val parametersList: List<Parameter>, val isFailable: Boolean, val throws: Boolean, val isProtocol: Boolean) {

  constructor(parameters: String, isFailable: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, false, false)
  constructor(parameters: String, isFailable: Boolean, throws: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, throws, false)
  constructor(parameters: String, isFailable: Boolean, throws: Boolean, isProtocol: Boolean): this(ParameterUtil.getParameters(parameters), isFailable, throws, isProtocol)
}
