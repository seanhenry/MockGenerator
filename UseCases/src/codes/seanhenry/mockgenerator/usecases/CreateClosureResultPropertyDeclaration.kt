package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Closure
import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateClosureResultPropertyDeclaration {

  fun transform(name: String, closure: Closure): PropertyDeclaration? {
    if (closure.arguments.isEmpty()) {
      return null
    }
    return PropertyDeclaration(transformName(name), transformType(closure))
  }

  private fun transformName(name: String): String {
    val suffix = AppendStringDecorator(null, "ClosureResult")
    val prefix = PrependStringDecorator(suffix, "stubbed")
    return prefix.process(name)
  }

  private fun transformType(closure: Closure): String {
    val arguments = closure.arguments.toMutableList()
    if (closure.arguments.size == 1) {
      arguments.add("Void")
    }
    return "(" + arguments.joinToString(", ") + ")"
  }
}
