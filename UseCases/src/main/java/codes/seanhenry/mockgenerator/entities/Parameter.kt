package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.Type

// TODO: change label and name to external/internal name
// TODO: make externalName optional
open class Parameter(val label: String, val name: String, val type: MethodType, val text: String) {

  // TODO: REMOVE THESE
  constructor(label: String, name: String, type: String, resolvedType: Type, text: String) : this(label, name, MethodType(Type(type), resolvedType, Type(type)), text)
  constructor(label: String, name: String, type: String, resolvedType: String, text: String) : this(label, name, type, Type(resolvedType), text)
  constructor(label: String, name: String, type: String, text: String) : this(label, name, type, type, text)

  val originalType = type.originalType.text
  val resolvedType = type.resolvedType.text
  // TODO: remove
  val erasedType = type.erasedType.text

  class Builder(private val externalName: String, private val internalName: String) {

    private var type = MethodType.IMPLICIT

    // TODO: external should start nil
    constructor(name: String): this("", name)

    fun type(string: String): Builder {
      val type = Type(string)
      this.type = MethodType(type, type, type)
      return this
    }

    fun type(): Type.Factory<Builder> {
      return Type.Factory(this) { this.type = MethodType(it, it, it) }
    }

    fun build(): Parameter {
      return Parameter(externalName, internalName, type, getText())
    }

    private fun getText(): String {
      var labels = internalName
      if (externalName.isNotEmpty()) {
        labels = "$externalName $labels"
      }
      return "$labels: ${type.originalType.text}"
    }
  }
}
