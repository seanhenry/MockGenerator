package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringClosureCall
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.usecases.CreateClosureCall
import codes.seanhenry.mockgenerator.usecases.CreateClosureResultPropertyDeclaration
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.*

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<ProtocolMethod>()
  private val classMethods = ArrayList<ProtocolMethod>()
  private val protocolProperties = ArrayList<ProtocolProperty>()
  private val classProperties = ArrayList<ProtocolProperty>()
  private var scope = ""
  private lateinit var nameGenerator: UniqueMethodNameGenerator

  override fun add(method: ProtocolMethod) {
    protocolMethods.add(method)
  }

  override fun add(vararg methods: ProtocolMethod) {
    addMethods(listOf(*methods))
  }

  override fun addMethods(methods: List<ProtocolMethod>) {
    protocolMethods.addAll(methods)
  }

  override fun setScope(scope: String) {
    this.scope = scope
  }

  override fun add(property: ProtocolProperty) {
    protocolProperties.add(property)
  }

  override fun add(vararg initialisers: Initialiser) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun add(vararg properties: ProtocolProperty) {
    addProperties(listOf(*properties))
  }

  override fun addInitialisers(initialisers: List<Initialiser>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addProperties(properties: List<ProtocolProperty>) {
    protocolProperties.addAll(properties)
  }

  override fun setClassInitialisers(vararg initialisers: Initialiser) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setClassInitialisers(initialisers: List<Initialiser>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassMethods(vararg methods: ProtocolMethod) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassMethods(methods: List<ProtocolMethod>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassProperties(vararg properties: ProtocolProperty) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun addClassProperties(properties: List<ProtocolProperty>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun generate(): String {
    generateOverloadedNames()
    val mockModel = MockViewModel(
        transformProperties(),
        transformMethods(),
        scope
    )
    view.render(mockModel)
    return ""
  }

  private fun generateOverloadedNames() {
    val protocolProperties = protocolProperties.map { toMethodModel(it) }
    val protocolMethods = protocolMethods.map { toMethodModel(it) }
    val classMethods = classMethods.map { toMethodModel(it) }
    val classProperties = classProperties.map { toMethodModel(it) }
    nameGenerator = UniqueMethodNameGenerator(protocolProperties + protocolMethods + classMethods + classProperties)
    nameGenerator.generateMethodNames()
  }

  private fun toMethodModel(method: ProtocolMethod): MethodModel {
    return MethodModel(method.name, method.parametersList)
  }

  private fun toMethodModel(property: ProtocolProperty): MethodModel {
    return MethodModel(property.name, "")
  }

  private fun transformProperties(): List<PropertyViewModel> {
    return protocolProperties.map {
      PropertyViewModel(
          getUniqueName(it).capitalize(),
          it.isWritable,
          it.type,
          OptionalUtil.removeOptional(it.type) + "?",
          OptionalUtil.removeOptionalRecursively(it.type) + "!",
          getDefaultValueAssignment(it.type),
          it.getTrimmedSignature()
      )
    }
  }

  private fun getDefaultValueAssignment(type: String): String {
    val defaultValue = DefaultValueStore().getDefaultValue(type)
    if (defaultValue != null) {
      return "= $defaultValue"
    }
    return ""
  }

  private fun transformMethods(): List<MethodViewModel> {
    return protocolMethods.map {
      MethodViewModel(
          getUniqueName(it).capitalize(),
          transformParameters(it),
          transformClosureParameters(it),
          transformReturnType(it),
          it.signature
      )
    }
  }

  private fun getUniqueName(method: ProtocolMethod) =
      nameGenerator.getMethodName(toMethodModel(method).id)!!
  private fun getUniqueName(property: ProtocolProperty) =
      nameGenerator.getMethodName(toMethodModel(property).id)!!

  private fun transformClosureParameters(method: ProtocolMethod): List<ClosureParameterViewModel> {
    return method.parametersList
        .mapNotNull { it as? ClosureParameter }
        .map {
          ClosureParameterViewModel(
              it.name.capitalize(),
              it.name,
              transformClosureToTupleDeclaration(it.closureArguments), // TODO: use same method as method params when closure model is complete
              transformClosureToImplicitTupleAssignment(it),
              it.closureArguments.isNotEmpty() // TODO: require proper closure model so this is done without string parsing
          )
        }
  }

  private fun transformClosureToTupleDeclaration(parameter: List<Parameter>): String {
    val closure = Closure("", parameter.map { it.text }, "", false)
    return CreateClosureResultPropertyDeclaration()
        .transform("", closure)?.type ?: ""
  }

  private fun transformClosureToImplicitTupleAssignment(closure: ClosureParameter): String {
    var tuple = ""
    if (closure.closureReturnType != "()" && closure.closureReturnType != "Void" && closure.closureReturnType != "(Void)") {
      tuple = "_ = "
    }
    tuple += closure.name
    if (closure.isOptional) {
      tuple += "?"
    }
    tuple += "("
    tuple += (0 until closure.closureArguments.size).joinToString(", ") {
      "result.$it"
    }
    return tuple + ")"
  }

  private fun transformReturnType(method: ProtocolMethod): ResultTypeViewModel? {
    val type = method.returnType
    if (type != null) {
      return ResultTypeViewModel(
          getDefaultValueAssignment(type),
          OptionalUtil.removeOptional(type) + "?",
          ClosureUtil.surroundClosure(OptionalUtil.removeOptionalRecursively(type)) + "!"
      // TODO: we need to surround all closures when appending an optional. Write some tests
      )
    }
    return null
  }

  private fun transformParameters(method: ProtocolMethod): ParametersViewModel? {
    if (method.parametersList.isEmpty()) {
      return null
    }
    val declaration = transformToTupleDeclaration(method.parametersList) ?: return null
    val assignment = transformToTupleAssignment(declaration) ?: return null
    return ParametersViewModel(
        declaration.type,
        assignment
    )
  }

  private fun transformToTupleDeclaration(parameters: List<Parameter>): TuplePropertyDeclaration? {
    return CreateInvokedParameters().transform("", parameters) // TODO: remove name
  }

  private fun transformToTupleAssignment(tuple: TuplePropertyDeclaration): String? {
    return SwiftStringTupleForwardCall().transform(tuple)
  }
}
