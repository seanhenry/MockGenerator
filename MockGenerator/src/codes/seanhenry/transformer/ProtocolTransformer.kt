package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Protocol
import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.SwiftProtocolDeclaration
import com.jetbrains.swift.psi.SwiftVisitor

class ProtocolTransformer: SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Protocol? {
      val visitor = ProtocolTransformer()
      element.accept(visitor)
      return visitor.transformedProtocol
    }
  }

  private var transformedProtocol: Protocol? = null

  override fun visitProtocolDeclaration(element: SwiftProtocolDeclaration) {
    val items = element.statementList
    val initializers = items.mapNotNull { InitializerTransformer.transform(it) }
    val methods = items.mapNotNull { FunctionTransformer.transform(it) }
    val properties = items.mapNotNull { VariableTransformer.transform(it) }
    // TODO: test cannot resolve
    var resolved = emptyList<PsiElement>()
    val clause = element.typeInheritanceClause
    if (clause != null) {
      resolved = clause.typeElementList.mapNotNull { Resolver.resolve(it) }
    }
    val protocols = resolved.mapNotNull { ProtocolTransformer.transform(it) }
    transformedProtocol = Protocol(initializers, properties, methods, protocols)
  }
}
