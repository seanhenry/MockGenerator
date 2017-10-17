package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.ClosureUtil
import codes.seanhenry.mockgenerator.util.ParameterUtil
import codes.seanhenry.mockgenerator.entities.Closure
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.util.GroupUtil

class CreateClosureCall {

  fun transform(parameters: List<Parameter>): List<Closure> {
    return parameters
        .filter { ClosureUtil.isClosure(it.type) }
        .map { Closure(it.name, getArguments(it.type), getReturnValue(it.type), getIsOptional(it.type)) }
  }

  private fun getArguments(parameter: String): List<String> {
    return ParameterUtil.getParameterList(parameter)
        .map { extractFirstClosureGroup(it) }
        .flatMap { toArguments(it) }
        .map { toType(it) }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
  }

  private fun extractFirstClosureGroup(it: String): String {
    return it.substring(findClosureArgument(it))
  }

  private fun findClosureArgument(string: String): IntRange {
    val groups = GroupUtil('(', ')').findGroups(string)
    (0 until groups.size)
        .mapNotNull { groups[it] }
        .forEach { group ->
          val isClosureGroup = string.substring(group.endInclusive).contains(closureMarker())
          if (isClosureGroup) {
            return stripParentheses(group)
          }
        }
    return IntRange(0, string.lastIndex)
  }

  private fun closureMarker(): String {
    return "->"
  }

  private fun toArguments(it: String): List<String> = it.split(",")
  private fun toType(it: String) = it.split(":").last()

  private fun getReturnValue(parameter: String): String {
    var result = parameter
    result = extractClosureReturnValue(result)
        .trim()
    if (result == "Void") {
      return ""
    }
    return result
  }

  private fun getIsOptional(it: String): Boolean {
    val trimmed = it.trim()
    return trimmed.endsWith(")?") || trimmed.endsWith(")!")
  }

  private fun extractClosureReturnValue(it: String): String {
    val range = findClosureArgument(it)
    val substring = it.substring(range.endInclusive + 1)
    if (substring.isEmpty()) {
      return substring
    }
    val returnRange = GroupUtil('(', ')').findGroups(substring)[0]
    if (returnRange == null) {
      return substring.substring(substring.indexOf(closureMarker()) + 2)
    } else {
      return substring.substring(stripParentheses(returnRange))
    }
  }

  private fun stripParentheses(range: IntRange): IntRange {
    return IntRange(range.start + 1, range.endInclusive - 1)
  }
}
