package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.*
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.swift.psi.*

class TypePatternTransformer : SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Type? {
      val visitor = TypePatternTransformer()
      element.accept(visitor)
      return visitor.transformedType
    }
  }

  private var transformedType: Type? = null

  override fun visitPatternInitializer(pattern: SwiftPatternInitializer) {
    pattern.pattern.accept(this)
    if (transformedType == null) {
      pattern.initializer?.expression?.accept(this)
    }
  }

  override fun visitTypeAnnotatedPattern(pattern: SwiftTypeAnnotatedPattern) {
    val typeElement = pattern.typeAnnotation.typeElement
    if (typeElement != null) {
      transformedType = TypeTransformer.transform(typeElement)
    }
  }

  override fun visitLiteralExpression(element: SwiftLiteralExpression) {
    transformedType = TypeIdentifier(element.type.presentableText)
  }

  override fun visitCallExpression(element: SwiftCallExpression) {
    element.invokedExpression?.accept(this)
  }

  override fun visitReferenceExpression(element: SwiftReferenceExpression) {
    val resolved = element.resolve()
    if (resolved is SwiftFunctionDeclaration) {
      transformedType = resolved.functionResult?.typeElement?.let { TypeTransformer.transform(it) }
    } else if (resolved is SwiftIdentifierPattern) {
      val pattern = PsiTreeUtil.getParentOfType(resolved, SwiftPatternInitializer::class.java)
      transformedType = pattern?.let { transform(it) }
    } else {
      transformedType = TypeIdentifier(element.text)
    }
  }

  override fun visitArrayDictionaryExpression(element: SwiftArrayDictionaryExpression) {
    if (element.isDictionary) {
      transformDictionary(element)
    } else {
      transformArray(element)
    }
  }

  private fun transformArray(element: SwiftArrayDictionaryExpression) {
    val type = transform(element.arrayItems[0])
    if (type != null) {
      transformedType = ArrayType(type, false)
    }
  }

  private fun transformDictionary(element: SwiftArrayDictionaryExpression) {
    val keyType = transform(element.dictionaryItems[0].key)
    val valueType = element.dictionaryItems[0].value?.let { transform(it) }
    if (keyType != null && valueType != null) {
      transformedType = DictionaryType(keyType, valueType, false)
    }
  }

  override fun visitComplexOperatorExpression(element: SwiftComplexOperatorExpression) {
    if (element.children.size < 3) { return }
    if (element.children[1] is SwiftIsOperator) {
      transformedType = TypeIdentifier("Bool")
    } else if (element.children[1] is SwiftAsOperator) {
      transformedType = element.children[2]?.let { TypeTransformer.transform(it) }
    }
  }

  override fun visitClosureExpression(element: SwiftClosureExpression) {
    val arguments = transformClosureArguments(element)
    val returnType = transformClosureReturnType(element)
    val throws = element.closureSignature?.asyncThrowsClause?.isThrows == true
    transformedType = FunctionType(arguments, returnType, throws)
    if (element.parent is SwiftCallExpression) {
      transformedType = returnType
    }
  }

  private fun transformClosureArguments(element: SwiftClosureExpression): List<Type> {
    return element.closureSignature?.closureParameterClause?.closureParameterList?.mapNotNull { parameter ->
      parameter.typeAnnotation?.typeElement?.let { type ->
        TypeTransformer.transform(type)
      }
    } ?: emptyList()
  }

  private fun transformClosureReturnType(element: SwiftClosureExpression): Type {
    return transformReturnTypeFromSignature(element)
        ?: transformReturnTypeStatements(element)
        ?: TypeIdentifier.EMPTY_TUPLE
  }

  private fun transformReturnTypeFromSignature(element: SwiftClosureExpression): Type? {
    return element.closureSignature?.functionResult?.typeElement?.let { TypeTransformer.transform(it) }
  }

  private fun transformReturnTypeStatements(element: SwiftClosureExpression): Type? {
    val returnStatement = element.statements.lastOrNull() as? SwiftReturnStatement
    return returnStatement?.expression?.let { transform(it) }
  }

  override fun visitParenthesizedExpression(element: SwiftParenthesizedExpression) {
    val elements = element.argumentList.argumentList.mapNotNull { it.expression?.let { transform(it) } }
    transformedType = TupleType(elements.map { TupleType.TupleElement(null, it) })
  }
}
