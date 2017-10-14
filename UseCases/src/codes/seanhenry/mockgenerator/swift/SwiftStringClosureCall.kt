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
    return "if let result = $propertyName {\n$closureCall\n}"
  }

  private fun createClosureCall(closure: Closure): String {
    val name = closure.name
    val arguments = closure.arguments
        .mapIndexed { i, _ -> "result.$i" }
        .joinToString(", ")
    val suppressWarning = if (!closure.returnValue.isEmpty()) "_ = " else ""
    val optional = if (closure.isOptional) "?" else ""
    return "$suppressWarning$name$optional($arguments)"
  }
}
