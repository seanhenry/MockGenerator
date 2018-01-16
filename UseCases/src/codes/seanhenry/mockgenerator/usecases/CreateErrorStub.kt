package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator
import codes.seanhenry.mockgenerator.util.StringDecorator

class CreateErrorStub {

  fun transform(name: String, throws: Boolean): PropertyDeclaration {
    if (!throws) {
      return PropertyDeclaration.EMPTY
    }
    val transformedName = getStringDecorator().process(name)
    return PropertyDeclaration(transformedName, "Error?")
  }

  private fun getStringDecorator(): StringDecorator {
    val append = AppendStringDecorator(null, "Error")
    return PrependStringDecorator(append, "stubbed")
  }
}
