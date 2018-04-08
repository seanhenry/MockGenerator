package codes.seanhenry.mockgenerator.transformer

import codes.seanhenry.mockgenerator.ast.*
import codes.seanhenry.mockgenerator.visitor.RecursiveVisitor

class TypeErasingVisitor(private val genericIdentifiers: List<String>) : RecursiveVisitor() {

  companion object {
    fun erase(type: Type, genericIdentifiers: List<String>) {
      val visitor = TypeErasingVisitor(genericIdentifiers)
      type.accept(visitor)
    }
  }

  override fun visitTypeIdentifier(type: TypeIdentifier) {
    if (genericIdentifiers.contains(type.firstIdentifier)) {
      type.identifiers = mutableListOf("Any")
    }
  }
}
