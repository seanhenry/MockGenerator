package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.*
import com.intellij.psi.PsiElement
import com.jetbrains.swift.psi.*

class TypeTransformer : SwiftVisitor() {

  companion object {
    fun transform(element: PsiElement): Type? {
      val visitor = TypeTransformer()
      element.accept(visitor)
      return visitor.transformedType
    }

    fun transformResolvedType(element: PsiElement): ResolvedType {
      val visitor = TypeTransformer()
      element.accept(visitor)
      val transformed = visitor.transformedType
      val resolved = Resolver.resolve(element)
      if (transformed != null) {
        return ResolvedType(transformed, resolved?.let { transform(it) } ?: transformed)
      }
      return ResolvedType.IMPLICIT
    }
  }

  private var transformedType: Type? = null

  override fun visitReferenceTypeElement(element: SwiftReferenceTypeElement) {
    val clause = element.genericArgumentClause
    transformedType = if (clause == null) {
      TypeIdentifier(element.text)
    } else {
      val genericTypes = clause.typeElementList
          .mapNotNull { transform(it) }
      GenericType(element.name, genericTypes)
    }
  }

  override fun visitOptionalTypeElement(element: SwiftOptionalTypeElement) {
    val transformed = transform(element.component)
    if (transformed != null) {
      transformedType = OptionalType(transformed, false, false)
    }
  }

  override fun visitImplicitlyUnwrappedOptionalTypeElement(element: SwiftImplicitlyUnwrappedOptionalTypeElement) {
    val transformed = transform(element.component)
    if (transformed != null) {
      transformedType = OptionalType(transformed, true, false)
    }
  }

  override fun visitArrayDictionaryTypeElement(element: SwiftArrayDictionaryTypeElement) {
    val keyType = element.keyTypeElement ?: return
    val transformedKeyType = transform(keyType) ?: return
    if (!element.isDictionary) {
      transformedType = ArrayType(transformedKeyType, false)
    } else {
      transform(element.valueTypeElement!!)?.let {
        transformedType = DictionaryType(transformedKeyType, it, false)
      }
    }
  }

  override fun visitTupleTypeElement(element: SwiftTupleTypeElement) {
    val elements = element.items
        .mapNotNull { item -> transform(item.typeElement)?.let { TupleType.TupleElement(item.name, it) } }
    transformedType = TupleType(elements)
  }

  override fun visitTupleTypeItem(element: SwiftTupleTypeItem) {
    transformedType = transform(element.typeElement)
  }

  override fun visitFunctionTypeElement(element: SwiftFunctionTypeElement) {
    val types = element
        .children
        .mapNotNull { it as? SwiftTypeElement }
    if (types.size != 2) {
      return
    }
    val tuple = types[0] as SwiftTupleTypeElement
    val arguments = tuple
        .items
        .mapNotNull { transform(it) }
    val returnType = transform(types[1])
    transformedType = FunctionType(arguments, returnType ?: TypeIdentifier.EMPTY_TUPLE, isThrowing(element.asyncThrowsClause))
  }

  private fun isThrowing(clause: SwiftAsyncThrowsClause?): Boolean {
    return clause?.isThrows ?: false
  }

  override fun visitInoutTypeElement(element: SwiftInoutTypeElement) {
    transformedType = transform(element.component!!)
  }

  override fun visitMetaTypeElement(element: SwiftMetaTypeElement) {
    transformedType = TypeIdentifier(element.text.split(".").toMutableList())
  }

  override fun visitTypeAliasDeclaration(element: SwiftTypeAliasDeclaration) {
    val type = element.typeAssignment?.typeElement ?: return
    val resolved = Resolver.resolve(type)
    if (resolved != null) {
      transformedType = transform(resolved)
    } else {
      transformedType = transform(type)
    }
  }

  override fun visitAnyTypeElement(element: SwiftAnyTypeElement) {
    transformedType = TypeIdentifier("Any")
  }

  override fun visitTypeElement(element: SwiftTypeElement) {
    transformedType = TypeIdentifier(element.text)
  }
}
