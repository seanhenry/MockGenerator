package codes.seanhenry.mockgenerator.entities

class TuplePropertyDeclaration(val parameters: List<TupleParameter>, val text: String) {

  constructor(parameters: List<TupleParameter>): this(parameters, createType(parameters))

  class TupleParameter(val name: String, val type: String, val resolvedType: String) {
    constructor(name: String, type: String): this(name, type, type)
  }

  companion object {
    private fun createType(parameters: List<TuplePropertyDeclaration.TupleParameter>) =
        "(" + parameters
            .map { buildParameterString(it) }
            .joinToString(", ") + ")"

    private fun buildParameterString(it: TuplePropertyDeclaration.TupleParameter): String {
      if (it.name.isEmpty()) {
        return it.type
      }
      return it.name + ": " + it.type
    }
  }
}

