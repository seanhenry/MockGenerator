package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.Closure

class SwiftStringClosureCall {

  fun transform(propertyName: String, closure: Closure): String {
    if (closure.arguments.isEmpty()) {
      return createClosureCall(closure)
    }
    return callClosureInIfStatement(propertyName, closure)
  }

  private fun callClosureInIfStatement(propertyName: String, closure: Closure): String {
    val closureCall = createClosureCall(closure)
    return """
        if let result = $propertyName {
        $closureCall
        }
        """.trimIndent()
  }

  private fun createClosureCall(closure: Closure): String {
    val arguments = closure.arguments
        .mapIndexed { i, _ -> "result.$i" }
        .joinToString(", ")
    var string = ""
    if (!closure.returnValue.isEmpty()) {
      string = "_ = "
    }
    return string + closure.name + "($arguments)"
  }
}
