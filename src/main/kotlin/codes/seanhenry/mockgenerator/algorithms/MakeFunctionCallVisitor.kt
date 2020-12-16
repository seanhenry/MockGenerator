package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Element
import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.visitor.Visitor

class MakeFunctionCallVisitor(private var result: String? = null) : Visitor() {
  companion object {
    fun make(element: Element): String? {
      val visitor = MakeFunctionCallVisitor()
      element.accept(visitor)
      return visitor.result
    }
  }

  override fun visitMethod(declaration: Method) {
    val arguments = declaration.parametersList.joinToString {
      val external = it.externalName
      val internal = it.internalName
      if (external == "_") {
        internal
      } else {
        "${external ?: internal}: ${internal}"
      }
    }
    result = "${declaration.name}($arguments)"
  }
}