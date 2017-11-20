package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvocationCount {

  fun transform(name: String) : PropertyDeclaration {
    val invokedMethodNameDecorator = PrependStringDecorator(null, "invoked")
    val invokedMethodCountNameDecorator = AppendStringDecorator(invokedMethodNameDecorator, "Count")
    return PropertyDeclaration(invokedMethodCountNameDecorator.process(name), "Int")
  }
}
