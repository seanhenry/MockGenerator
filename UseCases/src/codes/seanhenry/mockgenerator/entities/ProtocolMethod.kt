package codes.seanhenry.mockgenerator.entities

class ProtocolMethod(val name: String, val returnType: String?, val parameters: String, val signature: String) {

  val parameterList: List<String> = parameters
      .split(Regex(",(?=[\\w\\s`]+:)"))
      .map { it.trim(' ', '\n', '\t') }
      .filter { !it.isEmpty() }

}
