package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Class
import codes.seanhenry.mockgenerator.entities.Initializer
import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.SwiftClassDeclaration
import com.jetbrains.swift.psi.SwiftVisitor

class ClassTransformer : SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Class? {
      val visitor = ClassTransformer()
      element.accept(visitor)
      return visitor.transformedClass
    }
  }

  private var transformedClass: Class? = null

  override fun visitClassDeclaration(element: SwiftClassDeclaration) {
    if (element.name == "NSObject") {
      transformNSObject()
      return
    }
    val items = element.declarations
    val initializers = items.mapNotNull { InitializerTransformer.transform(it) }
    val methods = items.mapNotNull { FunctionTransformer.transform(it) }
    val properties = items.flatMap { VariableTransformer.transform(it) }
    var resolved = emptyList<PsiElement>()
    if (element.typeInheritanceClause != null) {
      resolved = element.typeInheritanceClause!!.typeElementList.mapNotNull { Resolver.resolve(it) }
    }
    val superclass = resolved.firstOrNull()?.let { ClassTransformer.transform(it) }
    transformedClass = Class(initializers, properties, methods, emptyList(), superclass)
  }

  private fun transformNSObject() {
    val defaultInit = Initializer(emptyList(), false, false)
    transformedClass = Class(listOf(defaultInit), emptyList(), emptyList(), emptyList(), null)
  }
}
