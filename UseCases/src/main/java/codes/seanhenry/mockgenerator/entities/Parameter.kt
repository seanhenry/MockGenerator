package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.ast.Type

// TODO: change label and name to external/internal name
// TODO: make externalName optional
open class Parameter(val label: String, val name: String, val type: MethodType, val text: String, val isEscaping: Boolean) {

  // TODO: REMOVE THESE
  constructor(label: String, name: String, type: MethodType, text: String) : this(label, name, type, text, false)
  constructor(label: String, name: String, type: String, resolvedType: Type, text: String) : this(label, name, MethodType(Type(type), resolvedType, Type(type)), text, false)
  constructor(label: String, name: String, type: String, resolvedType: String, text: String) : this(label, name, type, Type(resolvedType), text)
  constructor(label: String, name: String, type: String, text: String) : this(label, name, type, type, text)

  val originalType = type.originalType.text
  val resolvedType = type.resolvedType.text
  // TODO: remove
  val erasedType = type.erasedType.text

  class Builder(private val externalName: String, private val internalName: String) {

    private var type = MethodType.IMPLICIT
    private var isEscaping = false
    private val annotations = ArrayList<String>()
    private var isInout = false

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

    fun escaping(): Builder {
      isEscaping = true
      return annotation("@escaping")
    }

    fun annotation(annotation: String): Builder {
      annotations.add(annotation)
      return this
    }

    fun inout(): Builder {
      isInout = true
      return this
    }

    fun build(): Parameter {
      return Parameter(externalName, internalName, type, getText(), isEscaping)
    }

    private fun getText(): String {
      var labels = internalName
      if (externalName.isNotEmpty()) {
        labels = "$externalName $labels"
      }
      var annotations = ""
      if (!this.annotations.isEmpty()) {
        annotations = this.annotations.joinToString(" ") + " "
      }
      var inout = ""
      if (isInout) {
        inout = "inout "
      }
      return "$labels: $inout$annotations${type.originalType.text}"
    }
  }
}
