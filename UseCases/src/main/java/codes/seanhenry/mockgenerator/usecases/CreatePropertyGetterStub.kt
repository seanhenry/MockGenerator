package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.PrependStringDecorator
import codes.seanhenry.mockgenerator.util.StringDecorator

class CreatePropertyGetterStub : CreateStub() {

  override fun getStringDecorator(): StringDecorator {
    return PrependStringDecorator(null, "stubbed")
  }
}
