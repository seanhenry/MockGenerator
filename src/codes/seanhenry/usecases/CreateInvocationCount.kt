package codes.seanhenry.usecases

import codes.seanhenry.entities.IntPropertyDeclaration
import codes.seanhenry.util.AppendStringDecorator
import codes.seanhenry.util.PrependStringDecorator

class CreateInvocationCount {

  fun transform(name: String) : IntPropertyDeclaration {
    val invokedMethodNameDecorator = PrependStringDecorator(null, "invoked")
    val invokedMethodCountNameDecorator = AppendStringDecorator(invokedMethodNameDecorator, "Count")
    return IntPropertyDeclaration(invokedMethodCountNameDecorator.process(name), 0)
  }
}
