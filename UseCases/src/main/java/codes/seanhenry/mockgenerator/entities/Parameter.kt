package codes.seanhenry.mockgenerator.entities

// TODO: change label and name to external/internal name

open class Parameter(val label: String, val name: String, val type: String, val resolvedType: Type, val text: String) {

  constructor(label: String, name: String, type: String, resolvedType: String, text: String) : this(label, name, type, Type(resolvedType), text)
  constructor(label: String, name: String, type: String, text: String) : this(label, name, type, type, text)
}
