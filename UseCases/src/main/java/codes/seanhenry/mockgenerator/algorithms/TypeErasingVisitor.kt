package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.Type
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
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
