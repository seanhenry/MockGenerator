package codes.seanhenry.mockgenerator.entities

class Parameter(val label: String, val name: String, val type: String, val resolvedType: String, val text: String) {

  constructor(label: String, name: String, type: String, text: String) : this(label, name, type, type, text)
}
