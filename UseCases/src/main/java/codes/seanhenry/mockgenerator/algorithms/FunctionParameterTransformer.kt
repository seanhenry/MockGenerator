package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.FunctionType
import codes.seanhenry.mockgenerator.entities.OptionalType
import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.mockgenerator.generator.ClosureParameterViewModel
import codes.seanhenry.mockgenerator.visitor.RecursiveVisitor

class FunctionParameterTransformer(val name: String): RecursiveVisitor() {

  var transformed: ClosureParameterViewModel? = null
  private var isOptional = false

  override fun visitFunctionType(type: FunctionType) {
    transformed = ClosureParameterViewModel(
        name.capitalize(),
        name,
        transformClosureToTupleDeclaration(type.arguments),
        transformClosureToImplicitTupleAssignment(type),
        type.arguments.isNotEmpty()
    )
    super.visitFunctionType(type)
  }

  override fun visitOptionalType(type: OptionalType) {
    isOptional = true
    super.visitOptionalType(type)
  }

  private fun transformClosureToTupleDeclaration(parameters: List<Type>): String {
    return when {
      parameters.isEmpty() -> "()"
      parameters.size == 1 -> "(${parameters[0].text}, Void)"
      else -> "(${joinParameters(parameters)})"
    }
  }

  private fun joinParameters(parameters: List<Type>): String {
    return parameters.joinToString(", ") { it.text }
  }

  private fun transformClosureToImplicitTupleAssignment(function: FunctionType): String {
    val assignment = ArrayList<String>()
    supressWarning(assignment, function)
    tryNicely(assignment, function)
    call(assignment)
    addParameters(assignment, function)
    return assignment.joinToString("")
  }

  private fun supressWarning(assignment: MutableList<String>, function: FunctionType) {
    if (!TypeIdentifier.isVoid(function.returnType)) {
      assignment.add("_ = ")
    }
  }

  private fun tryNicely(assignment: MutableList<String>, function: FunctionType) {
    if (function.throws) {
      assignment.add("try? ")
    }
  }

  private fun call(assignment: MutableList<String>) {
    assignment.add(name)
    if (isOptional) {
      assignment.add("?")
    }
  }

  private fun addParameters(assignment: MutableList<String>, function: FunctionType) {
    assignment.add("(")
    assignment.add(listParameters(function))
    assignment.add(")")
  }

  private fun listParameters(function: FunctionType): String {
    return (0 until function.arguments.size).joinToString(", ") {
      "result.$it"
    }
  }
}
