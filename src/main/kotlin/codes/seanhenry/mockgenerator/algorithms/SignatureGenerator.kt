package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.visitor.Visitor

class SignatureGenerator: Visitor() {

  private var signature = ""

  companion object {
    fun signature(element: Element): String {
      val visitor = SignatureGenerator()
      element.accept(visitor)
      return visitor.signature
    }
  }

  override fun visitInitializer(declaration: Initializer) {
    signature = "init(${getParametersSignature(declaration.parametersList)})"
  }

  override fun visitProperty(declaration: Property) {
    signature = declaration.name
  }

  override fun visitMethod(declaration: Method) {
    val parameters = getParametersSignature(declaration.parametersList)
    val returnType = getReturnSignature(declaration)
    signature = "${declaration.name}($parameters)$returnType"
  }

  override fun visitSubscript(subscript: Subscript) {
    val parameters = getParametersSignature(subscript.parameters)
    signature = "subscript($parameters):${subscript.returnType.resolvedType.text}"
  }

  private fun getParametersSignature(parameters: List<Parameter>) =
      parameters.joinToString(",") { signature(it) }

  private fun getReturnSignature(declaration: Method): String {
    return when {
      declaration.returnType.resolvedType.text.isEmpty() -> ""
      else -> ":${declaration.returnType.resolvedType.text}"
    }
  }

  override fun visitParameter(parameter: Parameter) {
    val name = if (parameter.externalName == null || parameter.externalName.isEmpty()) {
      parameter.internalName
    } else {
      parameter.externalName
    }
    signature = "$name:${parameter.type.resolvedType.text}"
  }
}
