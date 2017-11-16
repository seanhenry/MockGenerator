package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserMethodCall
import codes.seanhenry.mockgenerator.util.DefaultValueStore
import codes.seanhenry.mockgenerator.util.OptionalUtil

class SwiftStringConvenienceInitCall {

  val store = DefaultValueStore()

  fun transform(call: InitialiserMethodCall): String {
    if (call.parameters.isEmpty()) {
      return "self.init()"
    }
    return "self.init(" + transformParameters(call).joinToString(", ") + ")"
  }

  private fun transformParameters(call: InitialiserMethodCall) =
      call.parameters.map {
        val value = getValue(it.label, it.type)
        if (it.label == "_") {
          value
        } else {
          it.label + ": " + value
        }
      }

  private fun getValue(label: String, type: String): String {
    val value = store.getDefaultValue(type)
    if (value != null) {
      return value
    } else if(OptionalUtil.isOptional(type)) {
      return "nil"
    } else {
      return "<#" + label + "#>"
    }
  }
}
