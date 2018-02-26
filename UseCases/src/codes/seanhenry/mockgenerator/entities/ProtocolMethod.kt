package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class ProtocolMethod(val name: String, val returnType: String?, val resolvedReturnType: Type?, val parametersList: List<Parameter>, val signature: String, val throws: Boolean) {

  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String, throws: Boolean): this(name, returnType, Type.create(returnType), parametersList, signature, throws)
  constructor(name: String, returnType: String?, parameters: String, signature: String): this(name, returnType, ParameterUtil.getParameters(parameters), signature, false)
  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String): this(name, returnType, parametersList, signature, false)
}
