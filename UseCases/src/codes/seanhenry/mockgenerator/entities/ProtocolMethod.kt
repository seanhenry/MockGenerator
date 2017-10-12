package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class ProtocolMethod(val name: String, val returnType: String?, val parameters: String, val signature: String) {

  val parameterList: List<String> = ParameterUtil.getParameterList(parameters)

}
