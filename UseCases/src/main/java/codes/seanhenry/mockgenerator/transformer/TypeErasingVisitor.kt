package codes.seanhenry.mockgenerator.transformer

import codes.seanhenry.mockgenerator.ast.*
import codes.seanhenry.mockgenerator.visitor.RecursiveVisitor

class TypeErasingVisitor(private val genericIdentifiers: List<String>): RecursiveVisitor() {

  override fun visitType(type: Type) {
    if (genericIdentifiers.contains(type.text)) {
      type.text = "Any"
    }
    super.visitType(type)
  }
}
