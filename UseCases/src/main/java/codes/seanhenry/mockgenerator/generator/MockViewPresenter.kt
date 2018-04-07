package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.ast.*
import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringConvenienceInitCall
import codes.seanhenry.mockgenerator.swift.SwiftStringInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringProtocolInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.usecases.CreateClosureResultPropertyDeclaration
import codes.seanhenry.mockgenerator.usecases.CreateConvenienceInitialiser
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.*
import codes.seanhenry.mockgenerator.visitor.RecursiveVisitor
import codes.seanhenry.mockgenerator.visitor.Visitor

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<Method>()
  private val classMethods = ArrayList<Method>()
  private val protocolProperties = ArrayList<Property>()
  private val classProperties = ArrayList<Property>()
  private var classInitialiser: Initialiser? = null
  private var initialisers = ArrayList<Initialiser>()
  private var scope: String? = null
  private lateinit var nameGenerator: UniqueMethodNameGenerator

  override fun setScope(scope: String) {
    this.scope = scope.trim()
  }

  override fun add(method: Method) {
    protocolMethods.add(method)
  }

  override fun add(property: Property) {
    protocolProperties.add(property)
  }

  override fun add(vararg initialisers: Initialiser) {
    addInitialisers(listOf(*initialisers))
  }

  override fun add(vararg methods: Method) {
    addMethods(listOf(*methods))
  }

  override fun add(vararg properties: Property) {
    addProperties(listOf(*properties))
  }

  override fun addInitialisers(initialisers: List<Initialiser>) {
    for (initialiser in initialisers) {
      this.initialisers.add(initialiser)
    }
  }

  override fun addMethods(methods: List<Method>) {
    for (method in methods) {
      this.protocolMethods.add(method)
    }
  }

  override fun addProperties(properties: List<Property>) {
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

  override fun addClassMethods(vararg methods: Method) {
    classMethods += methods
  }

  override fun addClassMethods(methods: List<Method>) {
    classMethods += methods
  }

  override fun addClassProperties(vararg properties: Property) {
    classProperties += properties
  }

  override fun addClassProperties(properties: List<Property>) {
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

  private fun toMethodModel(method: Method): MethodModel {
    return MethodModel(method.name, method.parametersList)
  }

  private fun toMethodModel(property: Property): MethodModel {
    return MethodModel(property.name, "")
  }

  private fun transformProperties(): List<PropertyViewModel> {
    return transformProperties(classProperties, true) + transformProperties(protocolProperties, false)
  }

  private fun transformProperties(properties: List<Property>, isClass: Boolean): List<PropertyViewModel> {
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

  private fun transformMethods(methods: List<Method>, isClass: Boolean): List<MethodViewModel> {
    return methods.map {
      MethodViewModel(
          getUniqueName(it).capitalize(),
          transformParameters(it),
          transformClosureParameters(it),
          transformReturnType(it),
          it.throws,
          transformDeclarationText(it.declarationText, isClass)
      )
    }
  }

  private fun getUniqueName(method: Method) =
      nameGenerator.getMethodName(toMethodModel(method).id)!!
  private fun getUniqueName(property: Property) =
      nameGenerator.getMethodName(toMethodModel(property).id)!!

  private fun transformClosureParameters(method: Method): List<ClosureParameterViewModel> {
    // TODO: extract and test
    class V(val name: String): RecursiveVisitor() {
      var transformed: ClosureParameterViewModel? = null
      var isOptional = false
      override fun visitFunctionType(type: FunctionType) {
        transformed = ClosureParameterViewModel(
            name.capitalize(),
            name,
            transformClosureToTupleDeclaration(type.parameters), // TODO: use same method as method params when closure model is complete
            transformClosureToImplicitTupleAssignment(name, type, isOptional),
            type.parameters.isNotEmpty()) // TODO: require proper closure model so this is done without string parsing
        super.visitFunctionType(type)
      }

      override fun visitOptionalType(type: OptionalType) {
        isOptional = true
        super.visitOptionalType(type)
      }
    }
    return method.parametersList
        .mapNotNull {
          val visitor = V(it.name)
          it.type.resolvedType.accept(visitor)
          visitor.transformed
        }
  }

  private fun transformClosureToTupleDeclaration(parameter: List<Type>): String {
    val closure = Closure("", parameter.map { it.text }, "", false)
    return CreateClosureResultPropertyDeclaration()
        .transform("", closure)?.type ?: ""
  }

  private fun transformClosureToImplicitTupleAssignment(name: String, closure: FunctionType, isOptional: Boolean): String {
    var tuple = ""
    if (!closure.returnType.isVoid) {
      tuple += "_ = "
    }
    if (closure.throws) {
      tuple += "try? "
    }
    tuple += name
    if (isOptional) {
      tuple += "?"
    }
    tuple += "("
    tuple += (0 until closure.parameters.size).joinToString(", ") {
      "result.$it"
    }
    return tuple + ")"
  }

  private fun transformReturnType(method: Method): ResultTypeViewModel? {
    val type = method.returnType
    if (!type.resolvedType.isEmpty) {
      return ResultTypeViewModel(
          getDefaultValueAssignment(type.erasedType.text),
          OptionalUtil.removeOptional(type.erasedType.text) + "?",
          ClosureUtil.surroundClosure(OptionalUtil.removeOptionalRecursively(type.erasedType.text)) + "!",
      // TODO: we need to surround all closures when appending an optional. Write some tests
        transformReturnCastStatement(type)
      )
    }
    return null
  }

  private fun transformReturnCastStatement(returnType: MethodType): String {
    if (returnType.originalType.text == returnType.erasedType.text) { return "" }
    var optional = "!"
    var type = returnType.originalType.text
    if (OptionalUtil.isOptional(type)) {
      optional = "?"
      type = OptionalUtil.removeOptional(type)
    }
    return " as$optional $type"
  }

  private fun transformParameters(method: Method): ParametersViewModel? {
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
