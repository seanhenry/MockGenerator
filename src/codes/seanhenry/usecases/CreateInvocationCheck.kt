package codes.seanhenry.usecases

import codes.seanhenry.entities.BoolPropertyDeclaration
import codes.seanhenry.util.PrependStringDecorator

class CreateInvocationCheck(val name: String) {

  fun transform() : BoolPropertyDeclaration {
    val decorator = PrependStringDecorator(null, "invoked")
    return BoolPropertyDeclaration(decorator.process(name), false)
  }
}
