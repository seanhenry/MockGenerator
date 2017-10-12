package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.ClosureUtil
import codes.seanhenry.mockgenerator.util.ParameterUtil
import codes.seanhenry.mockgenerator.entities.Closure

class CreateClosureCall {
  fun transform(parameters: List<String>): List<Closure> {
    return parameters
        .filter { ClosureUtil.isClosure(it) }
        .map { Closure(getName(it), getArguments(it)) }
  }

  private fun getName(parameter: String): String {
    return parameter.split(":")[0].trim()
  }

  private fun getArguments(parameter: String): List<String> {
    return ParameterUtil.getParameterList(parameter)
        .map { removeWhitespace(it) }
        .map { extractClosureArguments(it) }
        .flatMap { toArguments(it) }
        .map { toType(it) }
        .filter { it.isNotEmpty() }
  }

  private fun extractClosureArguments(it: String): String {
    val regex = Regex(".*:\\((.*)\\)->.*")
    return it.replace(regex, "$1")
  }
  private fun removeWhitespace(it: String): String = it.replace(Regex("\\s"), "")
  private fun toArguments(it: String): List<String> = it.split(",")
  private fun toType(it: String) = it.split(":").last()
}
