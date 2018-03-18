package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.ClosureUtil
import codes.seanhenry.mockgenerator.util.OptionalUtil
import codes.seanhenry.mockgenerator.util.StringDecorator

abstract class CreateStub {

  abstract fun getStringDecorator(): StringDecorator

  fun transform(name: String, type: String): PropertyDeclaration {
    val transformedName = getStringDecorator().process(name)
    var transformedType = ClosureUtil.surroundClosure(type)
    transformedType = OptionalUtil.removeOptionalRecursively(transformedType) + "!"
    return PropertyDeclaration(transformedName, transformedType)
  }
}
