package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator
import codes.seanhenry.mockgenerator.util.StringDecorator

class CreateMethodReturnStub: CreateStub() {

  override fun getStringDecorator(): StringDecorator {
    val prependDecorator = PrependStringDecorator(null, "stubbed")
    return AppendStringDecorator(prependDecorator, "Result")
  }
}
