package codes.seanhenry.mockgenerator.algorithms

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.visitor.Visitor

class DefaultValueVisitor: Visitor() {

  companion object {
    fun getDefaultValue(element: Element): String? {
      val visitor = DefaultValueVisitor()
      element.accept(visitor)
      return visitor.defaultValue
    }
  }

  var defaultValue: String? = null

  override fun visitTypeIdentifier(type: TypeIdentifier) {
    defaultValue = knownTypes[type.text]
  }

  override fun visitFunctionType(type: FunctionType) {
    defaultValue = buildDefaultValue(type)
  }

  private fun buildDefaultValue(type: FunctionType): String? {
    val value = ArrayList<String>()
    value.add("{")
    addArgumentWildcards(value, type)
    try {
      addReturnTypeDefaultValue(value, type)
    } catch (e: Error) {
      return null
    }
    value.add("}")
    return value.joinToString(" ")
  }

  private fun addArgumentWildcards(value: MutableList<String>, type: FunctionType) {
    if (type.arguments.isEmpty()) {
      return
    }
    value.add(type.arguments.joinToString(", ") { "_" })
    value.add("in")
  }

  private fun addReturnTypeDefaultValue(value: MutableList<String>, type: FunctionType) {
    if (TypeIdentifier.isVoid(type.returnType)) {
      return
    }
    val defaultValue = DefaultValueVisitor.getDefaultValue(type.returnType)
    if (defaultValue != null) {
      value.add("return")
      value.add(defaultValue)
    } else {
      throw Error()
    }
  }

  override fun visitOptionalType(type: OptionalType) {
    defaultValue = "nil"
  }

  override fun visitTupleType(type: TupleType) {
    if (type.types.isEmpty()) {
      defaultValue = "()"
    } else if (type.types.size == 1) {
      type.types[0].accept(this)
    } else {
      defaultValue = createDefaultTuple(type)
    }
  }

  private fun createDefaultTuple(type: TupleType): String? {
    val defaults = type.types.mapNotNull { getDefaultValue(it) }
    if (defaults.size == type.types.size) {
      return "(${defaults.joinToString(", ")})"
    }
    return null
  }

  override fun visitArrayType(type: ArrayType) {
    defaultValue = "[]"
  }

  override fun visitDictionaryType(type: DictionaryType) {
    defaultValue = "[:]"
  }

  override fun visitGenericType(type: GenericType) {
    defaultValue = knownGenericTypes[type.identifier]
  }

  private val knownTypes: Map<String, String> = mapOf(
      Pair("Double", "0"),
      Pair("Float", "0"),
      Pair("Int", "0"),
      Pair("Int16", "0"),
      Pair("Int32", "0"),
      Pair("Int64", "0"),
      Pair("Int8", "0"),
      Pair("UInt", "0"),
      Pair("UInt16", "0"),
      Pair("UInt32", "0"),
      Pair("UInt64", "0"),
      Pair("UInt8", "0"),
      Pair("Bool", "false"),
      Pair("UnicodeScalar", "\"!\""),
      Pair("Character", "\"!\""),
      Pair("StaticString", "\"\""),
      Pair("String", "\"\""),
      Pair("Void", "()")
  )

  private val knownGenericTypes: Map<String, String> = mapOf(
      Pair("Array", "[]"),
      Pair("ArraySlice", "[]"),
      Pair("ContiguousArray", "[]"),
      Pair("Set", "[]"),
      Pair("Dictionary", "[:]"),
      Pair("DictionaryLiteral", "[:]"),
      Pair("Optional", "nil")
  )
}
