package codes.seanhenry.mockgenerator.transformer

import codes.seanhenry.mockgenerator.ast.*
import codes.seanhenry.mockgenerator.visitor.RecursiveVisitor

class TypeErasingVisitor(private val genericIdentifiers: List<String>): RecursiveVisitor() {

  override fun visitTypeIdentifier(type: TypeIdentifier) {
    if (genericIdentifiers.contains(type.text)) {
      type.identifier = "Any"
    }
    super.visitTypeIdentifier(type)
  }
}
