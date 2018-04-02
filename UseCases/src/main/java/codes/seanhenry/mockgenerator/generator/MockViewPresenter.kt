package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringConvenienceInitCall
import codes.seanhenry.mockgenerator.swift.SwiftStringInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringProtocolInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.usecases.CreateClosureResultPropertyDeclaration
import codes.seanhenry.mockgenerator.usecases.CreateConvenienceInitialiser
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.*

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<ProtocolMethod>()
  private val classMethods = ArrayList<ProtocolMethod>()
  private val protocolProperties = ArrayList<ProtocolProperty>()
  private val classProperties = ArrayList<ProtocolProperty>()
  private var classInitialiser: Initialiser? = null
  private var initialisers = ArrayList<Initialiser>()
  private var scope: String? = null
  private lateinit var nameGenerator: UniqueMethodNameGenerator

  override fun setScope(scope: String) {
    this.scope = scope.trim()
  }

  override fun add(method: ProtocolMethod) {
    protocolMethods.add(method)
  }

  override fun add(property: ProtocolProperty) {
    protocolProperties.add(property)
  }

  override fun add(vararg initialisers: Initialiser) {
    addInitialisers(listOf(*initialisers))
  }

  override fun add(vararg methods: ProtocolMethod) {
    addMethods(listOf(*methods))
  }

  override fun add(vararg properties: ProtocolProperty) {
    addProperties(listOf(*properties))
  }

  override fun addInitialisers(initialisers: List<Initialiser>) {
    for (initialiser in initialisers) {
      this.initialisers.add(initialiser)
    }
  }

  override fun addMethods(methods: List<ProtocolMethod>) {
    for (method in methods) {
      this.protocolMethods.add(method)
    }
  }

  override fun addProperties(properties: List<ProtocolProperty>) {
    for (property in properties) {
      this.protocolProperties.add(property)
    }
  }

  override fun setClassInitialisers(vararg initialisers: Initialiser) {
    setClassInitialisers(listOf(*initialisers))
  }

  override fun setClassInitialisers(initialisers: List<Initialiser>) {
    classInitialiser = initialisers.minBy {
      it.parametersList.size
    }
  }

  override fun addClassMethods(vararg methods: ProtocolMethod) {
    classMethods += methods
  }

  override fun addClassMethods(methods: List<ProtocolMethod>) {
    classMethods += methods
  }

  override fun addClassProperties(vararg properties: ProtocolProperty) {
    classProperties += properties
  }

  override fun addClassProperties(properties: List<ProtocolProperty>) {
    classProperties += properties
  }

  override fun generate(): String {
    generateOverloadedNames()
    val mockModel = MockViewModel(
        transformInitializers(),
        transformProperties(),
        transformMethods(),
        scope
    )
    view.render(mockModel)
    return ""
  }

  private fun transformInitializers(): List<InitializerViewModel> {
    return transformClassInitializer() + transformProtocolInitializer()
  }

  private fun transformProtocolInitializer(): List<InitializerViewModel> {
    return initialisers.map {
      InitializerViewModel(
          SwiftStringProtocolInitialiserDeclaration().transform(it),
          "")
    }
  }

  private fun transformClassInitializer(): List<InitializerViewModel> {
    val init = classInitialiser
    if (init == null) {
      return emptyList()
    }
    var scope = this.scope
    if (scope == "open") {
      scope = "public "
    } else if (scope != null) {
      scope += " "
    } else {
      scope = ""
    }
    val call = CreateConvenienceInitialiser().transform(init)!!
    return listOf(InitializerViewModel(
        scope + SwiftStringInitialiserDeclaration().transform(call),
        SwiftStringConvenienceInitCall().transform(call)
    ))
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
    return transformProperties(classProperties, true) + transformProperties(protocolProperties, false)
  }

  private fun transformProperties(properties: List<ProtocolProperty>, isClass: Boolean): List<PropertyViewModel> {
    return properties.map {
      PropertyViewModel(
          getUniqueName(it).capitalize(),
          it.isWritable,
          it.type,
          OptionalUtil.removeOptional(it.type) + "?",
          OptionalUtil.removeOptionalRecursively(it.type) + "!",
          getDefaultValueAssignment(it.type),
          transformDeclarationText(it.getTrimmedSignature(), isClass))
    }
  }

  private fun transformDeclarationText(declaration: String, isOverriding: Boolean): String {
    var modifiers = ""
    if (isOverriding) {
      modifiers = "override "
    }
    if (scope != null) {
      modifiers += "$scope "
    }
    return "$modifiers$declaration"
  }

  private fun getDefaultValueAssignment(type: String): String {
    val defaultValue = DefaultValueStore().getDefaultValue(type)
    if (defaultValue != null) {
      return "= $defaultValue"
    }
    return ""
  }

  private fun transformMethods(): List<MethodViewModel> {
    return transformMethods(classMethods, true) + transformMethods(protocolMethods, false)
  }

  private fun transformMethods(methods: List<ProtocolMethod>, isClass: Boolean): List<MethodViewModel> {
    return methods.map {
      MethodViewModel(
          getUniqueName(it).capitalize(),
          transformParameters(it),
          transformClosureParameters(it),
          transformReturnType(it),
          it.throws,
          transformDeclarationText(it.signature, isClass)
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
      tuple += "_ = "
    }
    if (closure.throws) {
      tuple += "try? "
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
          getDefaultValueAssignment(type.erasedType.typeName),
          OptionalUtil.removeOptional(type.erasedType.typeName) + "?",
          ClosureUtil.surroundClosure(OptionalUtil.removeOptionalRecursively(type.erasedType.typeName)) + "!",
      // TODO: we need to surround all closures when appending an optional. Write some tests
        transformReturnCastStatement(type)
      )
    }
    return null
  }

  private fun transformReturnCastStatement(returnType: MethodType): String {
    if (returnType.originalType.typeName == returnType.erasedType.typeName) { return "" }
    var optional = "!"
    var type = returnType.originalType.typeName
    if (OptionalUtil.isOptional(type)) {
      optional = "?"
      type = OptionalUtil.removeOptional(type)
    }
    return " as$optional $type"
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
