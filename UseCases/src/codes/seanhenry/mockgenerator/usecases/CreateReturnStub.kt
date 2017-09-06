package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateReturnStub {

  fun transform(name: String, type: String): PropertyDeclaration {
    val prependDecorator = PrependStringDecorator(null, "stubbed")
    val decorator = AppendStringDecorator(prependDecorator, "Result")
    val transformedName = decorator.process(name)
    var transformedType = type
    while (transformedType.endsWith("?") || transformedType.endsWith("!")) {
      transformedType = transformedType.removeRange(transformedType.length - 1, transformedType.length)
    }
    transformedType += "!"
    return PropertyDeclaration(transformedName, transformedType)
  }
}
