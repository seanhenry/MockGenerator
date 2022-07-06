package codes.seanhenry.transformer

import codes.seanhenry.mockgenerator.entities.Parameter
import com.jetbrains.swift.psi.SwiftParameter
import com.jetbrains.swift.psi.SwiftParameterClause

class ParametersTransformer {

  companion object {
    fun transform(parameterClause: SwiftParameterClause?): List<Parameter> {
      if (parameterClause == null) {
        return emptyList()
      }
      return parameterClause.parameterList
          .filter { it.typeAnnotation?.typeElement != null }
          .map { toParameter(it) }
    }

    private fun toParameter(it: SwiftParameter) =
        Parameter(it.externalNameIdentifier?.text,
            it.internalNameIdentifier?.text ?: it.nameIdentifier?.text ?: "",
            TypeTransformer.transformResolvedType(it.typeAnnotation!!.typeElement!!),
            it.text,
            false)
  }
}
