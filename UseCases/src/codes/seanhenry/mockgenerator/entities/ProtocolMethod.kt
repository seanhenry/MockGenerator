package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class ProtocolMethod(val name: String, val returnType: String?, val parametersList: List<Parameter>, val signature: String, val throws: Boolean) {

  constructor(name: String, returnType: String?, parameters: String, signature: String): this(name, returnType, ParameterUtil.getParameters(parameters), signature, false)
  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String): this(name, returnType, parametersList, signature, false)
}
