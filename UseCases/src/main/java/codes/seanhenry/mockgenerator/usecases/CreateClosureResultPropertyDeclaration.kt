package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.Closure
import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateClosureResultPropertyDeclaration {

  fun transform(methodName: String, closure: Closure): PropertyDeclaration? {
    if (closure.arguments.isEmpty()) {
      return null
    }
    return PropertyDeclaration(transformName(methodName, closure), transformType(closure))
  }

  private fun transformName(methodName: String, closure: Closure): String {
    val suffix = AppendStringDecorator(null, "Result")
    val prefix = PrependStringDecorator(suffix, "stubbed")
    val methodNamePrefix = PrependStringDecorator(prefix, methodName)
    return methodNamePrefix.process(closure.name)
  }

  private fun transformType(closure: Closure): String {
    val arguments = closure.arguments.toMutableList()
    if (closure.arguments.size == 1) {
      arguments.add("Void")
    }
    return "(" + arguments.joinToString(", ") + ")"
  }
}
