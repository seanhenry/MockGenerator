package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.algorithms.*
import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringConvenienceInitCall
import codes.seanhenry.mockgenerator.swift.SwiftStringInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringProtocolInitialiserDeclaration
import codes.seanhenry.mockgenerator.swift.SwiftStringTupleForwardCall
import codes.seanhenry.mockgenerator.usecases.CreateConvenienceInitialiser
import codes.seanhenry.mockgenerator.usecases.CreateInvokedParameters
import codes.seanhenry.mockgenerator.util.*

class MockViewPresenter(val view: MockView): MockTransformer {

  private val protocolMethods = ArrayList<Method>()
  private val protocolProperties = ArrayList<Property>()
  private val protocolSubscripts = ArrayList<Subscript>()
  private val classMethods = ArrayList<Method>()
  private val classProperties = ArrayList<Property>()
  private var classSubscripts = ArrayList<Subscript>()
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

  override fun add(subscript: Subscript) {
    protocolSubscripts.add(subscript)
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

  override fun add(vararg subscripts: Subscript) {
    addSubscripts(listOf(*subscripts))
  }

  override fun addInitialisers(initializers: List<Initializer>) {
    for (initializer in initializers) {
      this.initialisers.add(initializer)
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

  override fun addSubscripts(subscripts: List<Subscript>) {
    for (subscript in subscripts) {
      this.protocolSubscripts.add(subscript)
    }
  }

  override fun setClassInitialisers(vararg initializers: Initializer) {
    setClassInitialisers(listOf(*initializers))
  }

  override fun setClassInitialisers(initializers: List<Initializer>) {
    classInitializer = initializers.minByOrNull {
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

  override fun addClassSubscripts(vararg subscripts: Subscript) {
    classSubscripts.addAll(subscripts)
  }

  override fun addClassSubscripts(subscripts: List<Subscript>) {
    classSubscripts.addAll(subscripts)
  }

  override fun generate(): String {
    generateOverloadedNames()
    val mockModel = MockViewModel(
        transformInitializers(),
        transformProperties(),
        transformMethods(),
        transformSubscripts(),
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
    val protocolSubscripts = protocolSubscripts.map { toMethodModel(it) }
    val classMethods = classMethods.map { toMethodModel(it) }
    val classProperties = classProperties.map { toMethodModel(it) }
    val classSubscripts = classSubscripts.map { toMethodModel(it) }
    nameGenerator = UniqueMethodNameGenerator(protocolProperties + protocolMethods + protocolSubscripts + classMethods + classProperties + classSubscripts)
    nameGenerator.generateMethodNames()
  }

  private fun toMethodModel(method: Method): MethodModel {
    return MethodModel(method.name, method.parametersList)
  }

  private fun toMethodModel(property: Property): MethodModel {
    return MethodModel(property.name, "")
  }

  private fun toMethodModel(subscript: Subscript): MethodModel {
    return MethodModel("subscript", subscript.parameters)
  }

  private fun transformProperties(): List<PropertyViewModel> {
    return transformProperties(classProperties, true) + transformProperties(protocolProperties, false)
  }

  private fun transformProperties(properties: List<Property>, isClass: Boolean): List<PropertyViewModel> {
    return properties.map {
      PropertyViewModel(
          it.name,
          getUniqueName(it).replaceFirstChar(Char::titlecase),
          it.isWritable,
          optionalizeIUO(it.type).text,
          surroundWithOptional(removeOptional(it.type), false).text,
          surroundWithOptional(removeOptionalRecursively(it.type), true).text,
          getDefaultValueAssignment(it.type),
          getDefaultValue(it.type),
          isClass,
          transformDeclarationText(it.getTrimmedDeclarationText(), isClass))
    }
  }

  private fun optionalizeIUO(type: Type): Type {
    return OptionalizeIUOVisitor.optionalize(type)
  }

  private fun removeOptional(type: Type): Type {
    return RemoveOptionalVisitor.removeOptional(type)
  }

  private fun removeOptionalRecursively(type: Type): Type {
    return RecursiveRemoveOptionalVisitor.removeOptional(type)
  }

  private fun surroundWithOptional(type: Type, unwrapped: Boolean): Type {
    return SurroundOptionalVisitor.surround(type, unwrapped)
  }

  private fun transformDeclarationText(declaration: String, isOverriding: Boolean): String {
    var modifiers = ""
    if (scope != null) {
      modifiers += "$scope "
    }
    if (isOverriding) {
      modifiers += "override "
    }
    return "$modifiers$declaration"
  }

  private fun getDefaultValueAssignment(type: Type): String {
    val defaultValue = DefaultValueVisitor.getDefaultValue(type)
    if (defaultValue != null && defaultValue != "nil") {
      return "= $defaultValue"
    }
    return ""
  }

  private fun getDefaultValue(type: Type): String? {
    return DefaultValueVisitor.getDefaultValue(type)
  }

  private fun transformMethods(): List<MethodViewModel> {
    return transformMethods(classMethods, true) + transformMethods(protocolMethods, false)
  }

  private fun transformMethods(methods: List<Method>, isClass: Boolean): List<MethodViewModel> {
    return methods.map { m ->
      MethodViewModel(
          getUniqueName(m).replaceFirstChar(Char::titlecase),
          transformParameters(m),
          m.parametersList.mapNotNull { transformClosureParameters(it) },
          transformReturnType(m),
          MakeFunctionCallVisitor.make(m),
          m.throws,
          m.rethrows,
          isClass,
          transformDeclarationText(m.declarationText.trim(), isClass)
      )
    }
  }

  private fun getUniqueName(method: Method) =
      nameGenerator.getMethodName(toMethodModel(method).id)!!
  private fun getUniqueName(property: Property) =
      nameGenerator.getMethodName(toMethodModel(property).id)!!
  private fun getUniqueName(subscript: Subscript) =
      nameGenerator.getMethodName(toMethodModel(subscript).id)!!

  private fun transformClosureParameters(parameter: Parameter): ClosureParameterViewModel? {
    val visitor = FunctionParameterTransformer(parameter.internalName)
    parameter.type.resolvedType.accept(visitor)
    return visitor.transformed
  }

  private fun transformReturnType(method: Method): ResultTypeViewModel? {
    val type = method.returnType
    if (!TypeIdentifier.isEmpty(type.resolvedType)) {
      return transformReturnType(type, method.genericParameters)
    }
    return null
  }

  private fun transformReturnType(type: ResolvedType, genericParameters: List<String>): ResultTypeViewModel {
    val erased = erase(type.originalType, genericParameters)
    return ResultTypeViewModel(
        getDefaultValueAssignment(type.resolvedType),
        getDefaultValue(type.resolvedType),
        surroundWithOptional(removeOptional(erased), false).text,
        surroundWithOptional(removeOptionalRecursively(erased), true).text,
        optionalizeIUO(erased).text,
        transformReturnCastStatement(type.originalType, erased)
    )
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
    if (originalType is OptionalType) {
      optional = "?"
      type = removeOptional(originalType).text
    }
    return " as$optional $type"
  }

  private fun transformParameters(method: Method): ParametersViewModel? {
    return transformParameters(method.parametersList, method.genericParameters)
  }

  private fun transformParameters(parametersList: List<Parameter>, genericParameters: List<String>): ParametersViewModel? {
    if (parametersList.isEmpty()) {
      return null
    }
    val declaration = transformToTupleDeclaration(parametersList, genericParameters) ?: return null
    val assignment = transformToTupleAssignment(declaration) ?: return null
    return ParametersViewModel(
        declaration.text,
        assignment
    )
  }

  private fun transformToTupleDeclaration(parameters: List<Parameter>, genericIdentifiers: List<String>): TuplePropertyDeclaration? {
    return CreateInvokedParameters().transform(parameters, genericIdentifiers)
  }

  private fun transformToTupleAssignment(tuple: TuplePropertyDeclaration): String? {
    return SwiftStringTupleForwardCall().transform(tuple)
  }

  private fun transformSubscripts(): List<SubscriptViewModel> {
    return transformSubscripts(classSubscripts, true) + transformSubscripts(protocolSubscripts, false)
  }

  private fun transformSubscripts(subscripts: List<Subscript>, isClass: Boolean): List<SubscriptViewModel> {
    return subscripts.map {
      SubscriptViewModel(
          getUniqueName(it).replaceFirstChar(Char::titlecase),
          transformParameters(it.parameters, emptyList()),
          it.isWritable,
          transformReturnType(it.returnType, emptyList()),
          MakeFunctionCallVisitor.make(it),
          isClass,
          transformSubscriptDeclarationText(it, isClass)
      )
    }
  }

  private fun transformSubscriptDeclarationText(subscript: Subscript, isOverriding: Boolean): String {
    var modifiers = ""
    if (scope != null) {
      modifiers += "${scope} "
    }
    if (isOverriding) {
      modifiers += "override "
    }
    return "$modifiers${subscript.declarationText}"
  }
}
