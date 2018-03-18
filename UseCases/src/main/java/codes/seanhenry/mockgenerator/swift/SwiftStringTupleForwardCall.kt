package codes.seanhenry.mockgenerator.swift

import codes.seanhenry.mockgenerator.entities.TuplePropertyDeclaration
import codes.seanhenry.mockgenerator.util.KeywordsStore

class SwiftStringTupleForwardCall {

  private val keywordStore = KeywordsStore()

  fun transform(property: TuplePropertyDeclaration): String {
    return "(" + property.parameters.map { buildParameter(it) }.joinToString(", ") + ")"
  }

  private fun buildParameter(parameter: TuplePropertyDeclaration.TupleParameter): String {
    if (parameter.type == "Void" || parameter.type == "()") {
      return "()"
    } else if (keywordStore.isSwiftKeyword(parameter.name)) {
      return "`" + parameter.name + "`"
    }
    return parameter.name
  }
}
