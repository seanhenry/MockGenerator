package codes.seanhenry.mockgenerator.usecases

import codes.seanhenry.mockgenerator.util.AppendStringDecorator
import codes.seanhenry.mockgenerator.util.PrependStringDecorator
import codes.seanhenry.mockgenerator.util.StringDecorator

class CreateInvokedParametersList: CreateParameterTuple() {

  override fun getStringDecorator(): StringDecorator {
    val invoked = PrependStringDecorator(null, "invoked")
    return AppendStringDecorator(invoked, "ParametersList")
  }
}
