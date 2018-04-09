package codes.seanhenry.mockgenerator.entities

// TODO: DELETE THIS
class Closure(val name: String,
              val arguments: List<String>,
              val returnValue: String,
              val isOptional: Boolean,
              val throws: Boolean) {

  constructor(name: String, arguments: List<String>, returnValue: String, isOptional: Boolean): this(name, arguments, returnValue, isOptional, false)
}
