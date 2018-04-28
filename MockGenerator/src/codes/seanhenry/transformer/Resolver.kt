package codes.seanhenry.transformer

import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.SwiftReferenceTypeElement
import com.jetbrains.swift.psi.SwiftVisitor

class Resolver : SwiftVisitor() {

  companion object {
    fun resolve(element: PsiElement): PsiElement? {
      val visitor = Resolver()
      element.accept(visitor)
      return visitor.resolved
    }
  }

  private var resolved: PsiElement? = null

  override fun visitReferenceTypeElement(swiftReferenceTypeElement: SwiftReferenceTypeElement) {
    resolved = swiftReferenceTypeElement.resolve()
  }
}
