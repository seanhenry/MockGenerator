package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.entities.TypeIdentifier
import codes.seanhenry.util.MySwiftPsiUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.swift.psi.*

class VariableTransformer: SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Property? {
      val visitor = VariableTransformer()
      element.accept(visitor)
      return visitor.transformedDeclaration
    }
  }

  private var transformedDeclaration: Property? = null

  override fun visitVariableDeclaration(element: SwiftVariableDeclaration) {
    val patternInitializer = element.patternInitializerList[0]
    val pattern = patternInitializer.pattern as SwiftTypeAnnotatedPattern
    val typeElement = pattern.typeAnnotation.typeElement
    if (shouldNotOverride(element) || typeElement == null) {
      return
    }
    val type = TypeTransformer.transform(typeElement) ?: TypeIdentifier.EMPTY
    transformedDeclaration = Property(patternInitializer.pattern.variables[0].name!!, type, isWritable(element), getDeclarationText(element, type.text))
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

  private fun getDeclarationText(element: SwiftVariableDeclaration, type: String): String {
    val startOffset = getStartOffset(element)
    val endOffset = getEndOffset(element)
    val signature = element.containingFile.text.substring(startOffset, endOffset)
    return if (MySwiftPsiUtil.hasExplicitType(element)) {
      signature
    } else "$signature: $type"
  }

  private fun getStartOffset(property: SwiftVariableDeclaration): Int {
    return property.textOffset + property.attributes.textLength
  }

  private fun getEndOffset(property: SwiftVariableDeclaration): Int {
    val initializer = PsiTreeUtil.findChildOfType(property, SwiftInitializer::class.java)
    var endOffset = property.textOffset + property.textLength
    if (initializer != null) {
      endOffset = initializer.textOffset
    }
    return endOffset
  }
}
