package codes.seanhenry.usecases

import codes.seanhenry.entities.BoolPropertyDeclaration
import codes.seanhenry.util.PrependStringDecorator

class CreateInvocationCheck(val initializer: Boolean) {

  fun transform(name: String) : BoolPropertyDeclaration {
    val decorator = PrependStringDecorator(null, "invoked")
    return BoolPropertyDeclaration(decorator.process(name), initializer)
  }
}
