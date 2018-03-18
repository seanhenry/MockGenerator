package codes.seanhenry.mockgenerator.entities

class Closure(val name: String,
              val arguments: List<String>, // TODO: make this List<Parameter> ?
              val returnValue: String,
              val isOptional: Boolean,
              val throws: Boolean) {

  constructor(name: String, arguments: List<String>, returnValue: String, isOptional: Boolean): this(name, arguments, returnValue, isOptional, false)
}

class ClosureParameter(label: String,
                       name: String,
                       type: String,
                       resolvedType: Type,
                       text: String,
                       val closureArguments: List<Parameter>,
                       val closureReturnType: String,
                       val isOptional: Boolean): Parameter(label, name, type, resolvedType, text)
