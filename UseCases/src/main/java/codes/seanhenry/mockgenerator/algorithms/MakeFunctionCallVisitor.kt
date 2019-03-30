package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Element
import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.entities.Subscript
import codes.seanhenry.mockgenerator.visitor.Visitor

class MakeFunctionCallVisitor : Visitor() {
  companion object {
    fun make(element: Element): String? {
      val visitor = MakeFunctionCallVisitor()
      element.accept(visitor)
      return visitor.result
    }
  }

  private var result: String? = null

  override fun visitMethod(declaration: Method) {
    result = "${declaration.name}(${parseParameters(declaration.parametersList, false)})"
  }

  override fun visitSubscript(subscript: Subscript) {
    result = "[${parseParameters(subscript.parameters, true)}]"
  }

  private fun parseParameters(parameters: List<Parameter>, useImplicitLabels: Boolean): String {
    return parameters.joinToString {
      val external = it.externalName
      val internal = it.internalName
      if (external == "_") {
        internal
      } else if (useImplicitLabels && external == null) {
        internal
      } else {
        "${external ?: internal}: ${internal}"
      }
    }
  }
}