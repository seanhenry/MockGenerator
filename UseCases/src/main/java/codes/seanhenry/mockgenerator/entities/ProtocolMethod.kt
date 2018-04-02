package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.util.ParameterUtil

class ProtocolMethod(val name: String, val returnType: MethodType?, val parametersList: List<Parameter>, val signature: String, val throws: Boolean) {

  constructor(name: String, returnType: String?, resolvedReturnType: Type?, parametersList: List<Parameter>, signature: String, throws: Boolean): this(name, MethodType.create(Type.create(returnType), resolvedReturnType, Type.create(returnType)), parametersList, signature, throws)
  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String, throws: Boolean): this(name, returnType, Type.create(returnType), parametersList, signature, throws)
  constructor(name: String, returnType: String?, parameters: String, signature: String): this(name, returnType, ParameterUtil.getParameters(parameters), signature, false)
  constructor(name: String, returnType: String?, parametersList: List<Parameter>, signature: String): this(name, returnType, parametersList, signature, false)

  var returnString: String? = returnType?.originalType?.typeName
  var resolvedReturnString: String? = returnType?.resolvedType?.typeName
}

class MethodType(val originalType: Type, val resolvedType: Type, val erasedType: Type) {

  constructor(originalString: String, resolvedString: String, erasedString: String): this(Type(originalString), Type(resolvedString), Type(erasedString))

  companion object {
    fun create(originalType: Type?, resolvedType: Type?, erasedType: Type?): MethodType? {
      if (originalType != null) {
        val resolved = resolvedType ?: originalType
        val erased = erasedType ?: originalType
        return MethodType(originalType, resolved, erased)
      }
      return null
    }
  }
}
