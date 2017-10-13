package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.ClosureUtil
import codes.seanhenry.mockgenerator.util.ParameterUtil
import codes.seanhenry.mockgenerator.entities.Closure
import codes.seanhenry.mockgenerator.entities.Parameter
import kotlin.ranges.IntRange.Companion.EMPTY

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

  private fun getFirstParenthesesGroup(it: String, reversed: Boolean = false): IntRange {
    val firstParenIndex = it.indexOf(openParenthesis(reversed))
    val firstClosure = it.indexOf(closureMarker(reversed))
    if (firstParenIndex == -1) {
      return EMPTY
    } else if (firstClosure in 0 until firstParenIndex) {
      return IntRange(0, firstClosure - 1)
    }
    val startIndex = firstParenIndex + 1
    var endIndex = firstParenIndex // inclusive
    var substring = it.substring(firstParenIndex)
    var parenDepth = 1
    do {
      substring = substring.substring(1)
      if (substring.startsWith(openParenthesis(reversed))) {
        parenDepth++
      } else if (substring.startsWith(closedParenthesis(reversed))) {
        parenDepth--
        if (parenDepth == 0)
          break
      }
      endIndex++
    } while (substring.length > 1)
    return IntRange(startIndex, endIndex)
  }

  private fun openParenthesis(reversed: Boolean): Char {
    return if (reversed) ')' else '('
  }

  private fun closedParenthesis(reversed: Boolean): Char {
    return if (reversed) '(' else ')'
  }

  private fun closureMarker(reversed: Boolean): String {
    return if (reversed) ">-" else "->"
  }

  private fun extractFirstClosureGroup(it: String, reversed: Boolean = false): String {
    var result = it
    do {
      val range = getFirstParenthesesGroup(result, reversed)
      val isClosureGroup = result.substring(range.endInclusive).contains(closureMarker(reversed))
      result = result.substring(range)
      if (isClosureGroup) {
        break
      }
    } while (range != EMPTY)
    return result
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

  private fun extractClosureReturnValue(it: String): String {
    return extractFirstClosureGroup(it.reversed(), true).reversed()
  }

  private fun getIsOptional(it: String): Boolean {
    val trimmed = it.trim()
    return trimmed.endsWith(")?") || trimmed.endsWith(")!")
  }
}
