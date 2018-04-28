package codes.seanhenry.mockgenerator.entities

import codes.seanhenry.mockgenerator.visitor.Visitor

open class Parameter(val externalName: String?, val internalName: String, val type: ResolvedType, val text: String, val isEscaping: Boolean): Element {

  val originalTypeText = type.originalType.text
  val resolvedTypeText = type.resolvedType.text

  override fun accept(visitor: Visitor) {
    visitor.visitParameter(this)
  }

  class Builder(private val externalName: String?, private val internalName: String) {

    private var type = ResolvedType.IMPLICIT
    private var isEscaping = false
    private val annotations = ArrayList<String>()
    private var isInout = false

    constructor(name: String): this(null, name)

    fun type(string: String): Builder {
      val type = TypeIdentifier(string)
      this.type = ResolvedType(type, type)
      return this
    }

    fun type(): TypeFactory<Builder> {
      return TypeFactory(this) { this.type = ResolvedType(it, it) }
    }

    fun resolvedType(): TypeFactory<Builder> {
      return TypeFactory(this) { this.type.resolvedType = it }
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
      if (externalName != null && externalName.isNotEmpty()) {
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
