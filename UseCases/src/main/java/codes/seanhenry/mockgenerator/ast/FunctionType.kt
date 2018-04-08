package codes.seanhenry.mockgenerator.ast

import codes.seanhenry.mockgenerator.visitor.Visitor

class FunctionType private constructor(val arguments: List<Type>, val returnType: Type, val throws: Boolean): Type("") {

  override var text: String = ""
    get() { return generateText() }

  private fun generateText(): String {
    val text = arguments.map { it.text }.joinToString(", ")
    var throwsText = ""
    if (throws) throwsText = "throws "
    return "($text) $throwsText-> ${returnType.text}"
  }

  override fun accept(visitor: Visitor) {
    visitor.visitFunctionType(this)
  }

  class Builder {

    private var returnType = Type.EMPTY_TUPLE
    private var throws = false
    private val arguments = ArrayList<Type>()

    fun throws(): Builder {
      throws = true
      return this
    }

    fun argument(type: String): Builder {
      this.arguments.add(Type(type))
      return this
    }

    fun argument(): Type.Factory<Builder> {
      return Factory(this) { this.arguments.add(it) }
    }

    fun returnType(type: String): Builder {
      this.returnType = Type(type)
      return this
    }

    fun returnType(): Type.Factory<Builder> {
      return Type.Factory(this) { type -> this.returnType = type }
    }

    fun build(): FunctionType {
      return FunctionType(arguments, returnType, throws)
    }
  }
}
