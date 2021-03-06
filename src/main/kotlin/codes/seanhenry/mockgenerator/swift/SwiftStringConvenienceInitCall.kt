package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.InitialiserCall
import codes.seanhenry.mockgenerator.util.DefaultValueStore
import codes.seanhenry.mockgenerator.util.OptionalUtil

class SwiftStringConvenienceInitCall {

  val store = DefaultValueStore()

  fun transform(call: InitialiserCall): String {
    if (call.parameters.isEmpty() && call.isFailable) {
      return "super.init()!"
    }
    val forceUnwrap = getForceUnwrap(call)
    val forceTry = getForceTry(call)
    val parameters = transformParameters(call).joinToString(", ")
    return "${forceTry}self.init($parameters)$forceUnwrap"
  }

  private fun getForceUnwrap(call: InitialiserCall): String = if (call.isFailable) "!" else ""
  private fun getForceTry(call: InitialiserCall): String = if (call.throws) "try! " else ""

  private fun transformParameters(call: InitialiserCall) =
      call.parameters.map {
        val value = getValue(it.externalName ?: it.internalName, it.originalTypeText)
        if (it.externalName == "_") {
          value
        } else if (it.externalName == null || it.externalName.isEmpty()) {
          it.internalName + ": " + value
        } else {
          it.externalName + ": " + value
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
