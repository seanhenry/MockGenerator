package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.BoolPropertyDeclaration
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvocationCheck(val initializer: Boolean) {

  fun transform(name: String) : BoolPropertyDeclaration {
    val decorator = PrependStringDecorator(null, "invoked")
    return BoolPropertyDeclaration(decorator.process(name), initializer)
  }
}
