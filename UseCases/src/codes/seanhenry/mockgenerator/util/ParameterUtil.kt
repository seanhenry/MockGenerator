package codes.seanhenry.mockgenerator.util

class ParameterUtil {
  companion object {
    fun getParameterList(parameters: String): List<String> {
      return parameters
          .split(Regex(",(?=[\\w\\s`]+:)"))
          .map { it.trim(' ', '\n', '\t') }
          .filter { it.isNotEmpty() }
    }
  }
}
