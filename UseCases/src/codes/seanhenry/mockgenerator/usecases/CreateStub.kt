package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.StringDecorator

abstract class CreateStub {

  abstract fun getStringDecorator(): StringDecorator

  fun transform(name: String, type: String): PropertyDeclaration {
    val transformedName = getStringDecorator().process(name)
    var transformedType = surroundClosure(type)
    transformedType = RemoveOptional.removeOptional(transformedType) + "!"
    return PropertyDeclaration(transformedName, transformedType)
  }

  private fun surroundClosure(type: String): String {
    if (!isClosure(type) || isClosureSurrounded(type)) {
      return type
    }
    return "($type)"
  }

  private fun isClosureSurrounded(type: String) = type.replace(" ", "").startsWith("((")

  private fun isClosure(type: String): Boolean {
    return type.contains("->")
  }
}
