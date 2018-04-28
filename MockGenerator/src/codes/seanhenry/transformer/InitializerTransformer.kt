package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Initializer
import codes.seanhenry.util.MySwiftPsiUtil
import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.SwiftInitializerDeclaration
import com.jetbrains.swift.psi.SwiftVisitor

class InitializerTransformer: SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Initializer? {
      val visitor = InitializerTransformer()
      element.accept(visitor)
      return visitor.transformedDeclaration
    }
  }

  private var transformedDeclaration: Initializer? = null

  override fun visitInitializerDeclaration(element: SwiftInitializerDeclaration) {
    if (shouldOverride(element)) {
      val parameters = ParametersTransformer.transform(element.parameterClause)
      transformedDeclaration = Initializer(parameters, MySwiftPsiUtil.isFailable(element), element.isThrowing)
    }
  }

  private fun shouldOverride(element: SwiftInitializerDeclaration): Boolean {
    return !MySwiftPsiUtil.isFilePrivate(element)
        && !MySwiftPsiUtil.isPrivate(element)
  }
}
