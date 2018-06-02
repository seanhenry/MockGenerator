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
    val transformed = transform(element.typeElement)
    if (transformed != null) {
      transformedType = OptionalType(transformed, false, false)
    }
  }

  override fun visitImplicitlyUnwrappedOptionalTypeElement(element: SwiftImplicitlyUnwrappedOptionalTypeElement) {
    val transformed = transform(element.typeElement)
    if (transformed != null) {
      transformedType = OptionalType(transformed, true, false)
    }
  }

  override fun visitArrayDictionaryTypeElement(element: SwiftArrayDictionaryTypeElement) {
    val keyType = transform(element.keyTypeElement) ?: return
    if (!element.isDictionary) {
      transformedType = ArrayType(keyType, false)
    } else {
      transform(element.valueTypeElement!!)?.let {
        transformedType = DictionaryType(keyType, it, false)
      }
    }
  }

  override fun visitTupleTypeElement(element: SwiftTupleTypeElement) {
    val elements = element.tupleTypeItemList
        .mapNotNull { item -> transform(item.typeElement)?.let { TupleType.TupleElement(item.name, it) } }
    transformedType = TupleType(elements)
  }

  override fun visitTupleTypeItem(element: SwiftTupleTypeItem) {
    transformedType = transform(element.typeElement)
  }

  override fun visitFunctionTypeElement(element: SwiftFunctionTypeElement) {
    if (element.typeElementList.size < 2) {
      return
    }
    val tuple = element.typeElementList[0] as SwiftTupleTypeElement
    val arguments = tuple.tupleTypeItemList
        .mapNotNull { transform(it) }
    val returnType = transform(element.typeElementList[1])
    transformedType = FunctionType(arguments, returnType ?: TypeIdentifier.EMPTY_TUPLE, isThrowing(element.throwsClause))
  }

  private fun isThrowing(clause: SwiftThrowsClause?): Boolean {
    return clause?.isThrows ?: false
  }

  override fun visitInoutTypeElement(element: SwiftInoutTypeElement) {
    transformedType = transform(element.typeElement!!)
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
