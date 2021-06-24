package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Method
import codes.seanhenry.mockgenerator.entities.ResolvedType
import codes.seanhenry.util.MySwiftPsiUtil
import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.SwiftFunctionDeclaration
import com.jetbrains.swift.psi.SwiftGenericParameterClause
import com.jetbrains.swift.psi.SwiftVisitor

class FunctionTransformer: SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Method? {
      val visitor = FunctionTransformer()
      element.accept(visitor)
      return visitor.transformedFunction
    }
  }

  private var transformedFunction: Method? = null

  override fun visitFunctionDeclaration(element: SwiftFunctionDeclaration) {
    if (shouldNotOverride(element)) {
      return
    }
    val genericParameters = transformGenericParameters(element.genericParameterClause)
    val resolvedReturnType = transformReturnType(element)
    val declarationText = getDeclarationText(element)
    val parameters = ParametersTransformer.transform(element.parameterClause)
    transformedFunction = Method(
        element.name ?: "",
        genericParameters,
        resolvedReturnType,
        parameters,
        declarationText,
        element.asyncThrowsClause?.isThrows == true,
        element.asyncThrowsClause?.isRethrows == true)
  }

  private fun transformGenericParameters(clause: SwiftGenericParameterClause?): List<String> {
    return if (clause == null) {
      emptyList()
    } else clause.genericParameterList.mapNotNull { it.name }
  }

  private fun getDeclarationText(element: SwiftFunctionDeclaration): String {
    val codeBlock = element.codeBlock
    val methodOffset = element.startOffsetInParent
    val startOffset = methodOffset + element.attributes.startOffsetInParent + element.attributes.textLength
    var endOffset = methodOffset + element.textLength
    if (codeBlock != null) {
      endOffset = methodOffset + codeBlock.startOffsetInParent
    }
    return element.containingClass!!.text.substring(startOffset, endOffset)
  }

  private fun transformReturnType(element: SwiftFunctionDeclaration): ResolvedType {
    var resolvedReturnType = ResolvedType.IMPLICIT
    val typeElement = element.functionResult?.typeElement
    val returnType = typeElement?.let { TypeTransformer.transform(it) }
    if (returnType != null) {
      resolvedReturnType = ResolvedType(returnType, returnType)
    }
    return resolvedReturnType
  }

  private fun shouldNotOverride(element: SwiftFunctionDeclaration): Boolean {
    return (element.isStatic
        || MySwiftPsiUtil.isFinal(element)
        || MySwiftPsiUtil.isPrivate(element)
        || MySwiftPsiUtil.isFilePrivate(element))
  }
}
