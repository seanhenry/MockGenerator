package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.util.MySwiftPsiUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.swift.psi.*

class VariableTransformer: SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): List<Property> {
      val visitor = VariableTransformer()
      element.accept(visitor)
      return visitor.transformedDeclarations
    }
  }

  private var transformedDeclarations = emptyList<Property>()

  override fun visitVariableDeclaration(element: SwiftVariableDeclaration) {
    if (shouldNotOverride(element)) {
          return
        }
    val isWritable = isWritable(element)
    transformedDeclarations = element.patternInitializerList.mapNotNull { transformVariable(it, isWritable) }
  }

  private fun transformVariable(patternInitializer: SwiftPatternInitializer, isWritable: Boolean): Property? {
    val type = TypePatternTransformer.transform(patternInitializer) ?: return null
    val name = patternInitializer.variables[0].name!!
    return Property(name, type, isWritable, getDeclarationText(patternInitializer, type.text))
  }

  private fun shouldNotOverride(element: SwiftVariableDeclaration): Boolean {
    return (element.isConstant
        || element.isStatic
        || MySwiftPsiUtil.isFinal(element)
        || MySwiftPsiUtil.isPrivate(element)
        || MySwiftPsiUtil.isFilePrivate(element))
  }

  private fun isWritable(element: SwiftVariableDeclaration): Boolean {
    return if (element.containingClass is SwiftProtocolDeclaration) {
      PsiTreeUtil.findChildOfType(element, SwiftSetterClause::class.java) != null
    } else {
      !MySwiftPsiUtil.isComputed(element) && !MySwiftPsiUtil.isPrivateSet(element) && !MySwiftPsiUtil.isFilePrivateSet(element)
    }
  }

  private fun getDeclarationText(patternInitializer: SwiftPatternInitializer, type: String): String {
    val name = patternInitializer.variables[0].nameIdentifier?.text
        ?: patternInitializer.variables[0].text
    return "var $name: $type"
  }
}
