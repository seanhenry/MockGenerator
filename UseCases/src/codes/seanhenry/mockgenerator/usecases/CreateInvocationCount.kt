package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.IntPropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator

class CreateInvocationCount {

  fun transform(name: String) : IntPropertyDeclaration {
    val invokedMethodNameDecorator = PrependStringDecorator(null, "invoked")
    val invokedMethodCountNameDecorator = AppendStringDecorator(invokedMethodNameDecorator, "Count")
    return IntPropertyDeclaration(invokedMethodCountNameDecorator.process(name), 0)
  }
}
