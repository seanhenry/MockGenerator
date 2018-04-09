package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringConvenienceInitCall
import codes.seanhenry.mockgenerator.swift.SwiftStringInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringProtocolInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.algorithms.CopyVisitor
import codes.seanhenry.mockgenerator.algorithms.DefaultValueVisitor
import codes.seanhenry.mockgenerator.algorithms.FunctionParameterTransformer
import codes.seanhenry.mockgenerator.algorithms.TypeErasingVisitor
import codes.seanhenry.mockgenerator.usecases.CreateConvenienceInitialiser
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.*

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<Method>()
  private val classMethods = ArrayList<Method>()
  private val protocolProperties = ArrayList<Property>()
  private val classProperties = ArrayList<Property>()
  private var classInitializer: Initializer? = null
  private var initialisers = ArrayList<Initializer>()
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

  override fun add(vararg initializers: Initializer) {
    addInitialisers(listOf(*initializers))
  }

  override fun add(vararg methods: Method) {
    addMethods(listOf(*methods))
  }

  override fun add(vararg properties: Property) {
    addProperties(listOf(*properties))
  }

  override fun addInitialisers(initializers: List<Initializer>) {
    for (initialiser in initializers) {
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

  override fun setClassInitialisers(vararg initializers: Initializer) {
    setClassInitialisers(listOf(*initializers))
  }

  override fun setClassInitialisers(initializers: List<Initializer>) {
    classInitializer = initializers.minBy {
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
    val init = classInitializer
    val call = flatMap(classInitializer) { CreateConvenienceInitialiser().transform(it) }
    if (init != null && call != null) {
      val scope = getClassInitialiserScope()
      return listOf(InitializerViewModel(
          scope + SwiftStringInitialiserDeclaration().transform(call),
          SwiftStringConvenienceInitCall().transform(call)
      ))
    }
    return emptyList()
  }

  private fun <T, R> flatMap(value: T?, transform: (T) -> R): R? {
    if (value != null) {
      return transform(value)
    }
    return null
  }

  private fun getClassInitialiserScope(): String {
    return if (scope == "open") {
      "public "
    } else if (scope != null) {
      "${this.scope} "
    } else {
      ""
    }
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
          it.type.text,
          OptionalUtil.removeOptional(it.type.text) + "?", // TODO: stop using optUtil and use visitor
          OptionalUtil.removeOptionalRecursively(it.type.text) + "!", // TODO: same as above
          getDefaultValueAssignment(it.type),
          transformDeclarationText(it.getTrimmedDeclarationText(), isClass))
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

  private fun getDefaultValueAssignment(type: Type): String {
    val visitor = DefaultValueVisitor()
    type.accept(visitor)
    val defaultValue = visitor.defaultValue
    if (defaultValue != null && defaultValue != "nil") {
      return "= $defaultValue"
    }
    return ""
  }

  private fun transformMethods(): List<MethodViewModel> {
    return transformMethods(classMethods, true) + transformMethods(protocolMethods, false)
  }

  private fun transformMethods(methods: List<Method>, isClass: Boolean): List<MethodViewModel> {
    return methods.map { m ->
      MethodViewModel(
          getUniqueName(m).capitalize(),
          transformParameters(m),
          m.parametersList.mapNotNull { transformClosureParameters(it) },
          transformReturnType(m),
          m.throws,
          transformDeclarationText(m.declarationText, isClass)
      )
    }
  }

  private fun getUniqueName(method: Method) =
      nameGenerator.getMethodName(toMethodModel(method).id)!!
  private fun getUniqueName(property: Property) =
      nameGenerator.getMethodName(toMethodModel(property).id)!!

  private fun transformClosureParameters(parameter: Parameter): ClosureParameterViewModel? {
    val visitor = FunctionParameterTransformer(parameter.name)
    parameter.type.resolvedType.accept(visitor)
    return visitor.transformed
  }

  private fun transformReturnType(method: Method): ResultTypeViewModel? {
    val type = method.returnType
    if (!TypeIdentifier.isEmpty(type.resolvedType)) {
      val erased = erase(type.originalType, method.genericParameters)
      return ResultTypeViewModel(
          getDefaultValueAssignment(type.resolvedType),
          OptionalUtil.removeOptional(erased.text) + "?",
          ClosureUtil.surroundClosure(OptionalUtil.removeOptionalRecursively(erased.text)) + "!",
      // TODO: we need to surround all closures when appending an optional. Write some tests
        transformReturnCastStatement(type.originalType, erased)
      )
    }
    return null
  }

  private fun erase(type: Type, genericParameters: List<String>): Type {
    val copied = copy(type)
    val visitor = TypeErasingVisitor(genericParameters)
    copied.accept(visitor)
    return copied
  }

  private fun copy(type: Type): Type {
    val copyVisitor = CopyVisitor()
    type.accept(copyVisitor)
    return copyVisitor.copy
  }

  private fun transformReturnCastStatement(originalType: Type, erasedType: Type): String {
    if (originalType.text == erasedType.text) {
      return ""
    }
    var optional = "!"
    var type = originalType.text
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
    val declaration = transformToTupleDeclaration(method.parametersList, method.genericParameters) ?: return null
    val assignment = transformToTupleAssignment(declaration) ?: return null
    return ParametersViewModel(
        declaration.type,
        assignment
    )
  }

  private fun transformToTupleDeclaration(parameters: List<Parameter>, genericIdentifiers: List<String>): TuplePropertyDeclaration? {
    return CreateInvokedParameters().transform("", parameters, genericIdentifiers) // TODO: remove name
  }

  private fun transformToTupleAssignment(tuple: TuplePropertyDeclaration): String? {
    return SwiftStringTupleForwardCall().transform(tuple)
  }
}
