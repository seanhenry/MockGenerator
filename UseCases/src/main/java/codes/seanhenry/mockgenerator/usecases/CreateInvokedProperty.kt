package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvokedProperty {

  fun transform(name: String, type: String): PropertyDeclaration {
    val decorator = PrependStringDecorator(null, "invoked")
    return PropertyDeclaration(decorator.process(name), type)
  }
}
