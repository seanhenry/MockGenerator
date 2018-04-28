package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Class
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
    val items = element.statementList
    val initializers = items.mapNotNull { InitializerTransformer.transform(it) }
    val methods = items.mapNotNull { FunctionTransformer.transform(it) }
    val properties = items.mapNotNull { VariableTransformer.transform(it) }
    // TODO: test cannot resolve
    var resolved = emptyList<PsiElement>()
    if (element.typeInheritanceClause != null) {
      resolved = element.typeInheritanceClause!!.typeElementList.mapNotNull { Resolver.resolve(it) }
    }
    val protocols = resolved.mapNotNull { ProtocolTransformer.transform(it) }
    transformedClass = Class(initializers, properties, methods, null, protocols, null)
  }
}
