package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvocationCheck {

  fun transform(name: String) : PropertyDeclaration {
    val decorator = PrependStringDecorator(null, "invoked")
    return PropertyDeclaration(decorator.process(name), "Bool")
  }
}
