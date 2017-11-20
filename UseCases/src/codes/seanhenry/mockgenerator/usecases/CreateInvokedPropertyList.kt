package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvokedPropertyList {

  fun transform(name: String, type: String): PropertyDeclaration {
    val prepend = PrependStringDecorator(null, "invoked")
    val decorator = AppendStringDecorator(prepend, "List")
    return PropertyDeclaration(decorator.process(name), type)
  }
}

