package codes.seanhenry.mockgenerator.generator

import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringReturnProperty
import codes.seanhenry.mockgenerator.swift.*
import codes.seanhenry.mockgenerator.usecases.*
import codes.seanhenry.mockgenerator.util.DefaultValueStore
import codes.seanhenry.mockgenerator.util.MethodModel
import codes.seanhenry.mockgenerator.util.UniqueMethodNameGenerator

class MockGenerator: MockTransformer {

  private val protocolMethods = ArrayList<Method>()
  private val classMethods = ArrayList<Method>()
  private val protocolProperties = ArrayList<Property>()
  private val classProperties = ArrayList<Property>()
  private var scope = ""
  private var override = false
  private lateinit var nameGenerator: UniqueMethodNameGenerator
  private var lines = ArrayList<String>()
  private var classInitialiser: Initialiser? = null
  private var initialisers = ArrayList<Initialiser>()

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
    lines = ArrayList()
    generateOverloadedNames()

    appendInitialisers()

    override = true
    appendPropertyMocks(classProperties)
    override = false

    appendPropertyMocks(protocolProperties)

    override = true
    appendMethodMocks(classMethods)
    override = false

    appendMethodMocks(protocolMethods)

    return lines.joinToString("\n")
  }

  private fun appendInitialisers() {
    val classInitialiser = this.classInitialiser
    if (classInitialiser != null) {
      appendClassInitialiser(classInitialiser)
    }
    for (initialiser in initialisers) {
      appendProtocolInitialiser(initialiser)
    }
  }

  private fun appendProtocolInitialiser(initialiser: Initialiser) {
    addInitialiserScopedLine(SwiftStringProtocolInitialiserDeclaration().transform(initialiser) + " {\n}")
  }

  private fun appendClassInitialiser(initialiser: Initialiser) {
    val call = CreateConvenienceInitialiser().transform(initialiser)
    if (call != null) {
      addInitialiserScopedLine(SwiftStringInitialiserDeclaration().transform(call) + " {")
      addLine(SwiftStringConvenienceInitCall().transform(call))
      addLine("}")
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

  private fun appendPropertyMocks(properties: List<Property>) {
    for (property in properties) {
      val name = nameGenerator.getMethodName(toMethodModel(property).id) ?: continue
      val setterName = name + "Setter"
      val setterInvocationCheck = CreateInvocationCheck().transform(setterName)
      val setterInvocationCount = CreateInvocationCount().transform(setterName)
      val invokedProperty = CreateInvokedProperty().transform(name, TransformToOptional().transform(property.type.text))
      val invokedPropertyList = CreateInvokedPropertyList().transform(name, property.type.text)
      val getterName = name + "Getter"
      val getterInvocationCheck = CreateInvocationCheck().transform(getterName)
      val getterInvocationCount = CreateInvocationCount().transform(getterName)
      val returnStub = CreatePropertyGetterStub().transform(name, property.type.text)
      addSetterProperties(setterInvocationCheck, setterInvocationCount, invokedProperty, invokedPropertyList, property.isWritable)
      addGetterProperties(property, getterInvocationCheck, getterInvocationCount, returnStub)
      addPropertyDeclaration(property)
      addSetterBlock(setterInvocationCheck, setterInvocationCount, invokedProperty, invokedPropertyList, property.isWritable)
      addGetterBlock(getterInvocationCheck, getterInvocationCount, returnStub, property.isWritable)
      addClosingBrace()
    }
  }

  private fun addGetterBlock(getterInvocationCheck: PropertyDeclaration,
                             getterInvocationCount: PropertyDeclaration,
                             returnStub: PropertyDeclaration,
                             isWritable: Boolean) {
    if (isWritable) {
      addLine("get {")
    }
    addPropertyInvocationCheckStatements(getterInvocationCheck, getterInvocationCount)
    addLine(SwiftStringReturnProperty().transform(returnStub))
    if (isWritable) {
      addClosingBrace()
    }
  }

  private fun addSetterBlock(setterInvocationCheck: PropertyDeclaration,
                             setterInvocationCount: PropertyDeclaration,
                             invokedProperty: PropertyDeclaration,
                             invokedPropertyList: PropertyDeclaration,
                             isWritable: Boolean) {
    if (isWritable) {
      addLine("set {")
      addPropertyInvocationCheckStatements(setterInvocationCheck, setterInvocationCount)
      addPropertyInvocationCaptureStatements(invokedProperty, invokedPropertyList)
      addClosingBrace()
    }
  }

  private fun addPropertyDeclaration(property: Property) {
    addOverriddenLine(property.getTrimmedDeclarationText() + " {")
  }

  private fun addSetterProperties(setterInvocationCheck: PropertyDeclaration,
                                  setterInvocationCount: PropertyDeclaration,
                                  invokedProperty: PropertyDeclaration,
                                  invokedPropertyList: PropertyDeclaration,
                                  isWritable: Boolean) {
    if (isWritable) {
      addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(setterInvocationCheck, "false"))
      addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(setterInvocationCount, "0"))
      addScopedLine(SwiftStringPropertyDeclaration().transform(invokedProperty))
      addScopedLine(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedPropertyList))
    }
  }

  private fun addGetterProperties(property: Property,
                                  getterInvocationCheck: PropertyDeclaration,
                                  getterInvocationCount: PropertyDeclaration,
                                  returnStub: PropertyDeclaration) {
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCheck, "false"))
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCount, "0"))
    val defaultValue = DefaultValueStore().getDefaultValue(property.type.text)
    addScopedLine(SwiftStringDefaultValuePropertyDeclaration().transform(returnStub, defaultValue))
  }

  private fun addClosingBrace() {
    addLine("}")
  }

  private fun addPropertyInvocationCheckStatements(invocationCheck: PropertyDeclaration,
                                                   invocationCount: PropertyDeclaration) {
    addLine(SwiftStringPropertyAssignment().transform(invocationCheck, "true"))
    addLine(SwiftStringIncrementAssignment().transform(invocationCount))
  }

  private fun addPropertyInvocationCaptureStatements(invokedProperty: PropertyDeclaration,
                                                     invokedPropertyList: PropertyDeclaration) {
    addLine(SwiftStringPropertyAssignment().transform(invokedProperty, "newValue"))
    addLine(SwiftStringArrayAppender().transform(invokedPropertyList, "newValue"))
  }

  private fun appendMethodMocks(methods: List<Method>) {
    for (method in methods) {
      val name = nameGenerator.getMethodName(toMethodModel(method).id) ?: continue
      val invocationCheck = CreateInvocationCheck().transform(name)
      val invocationCount = CreateInvocationCount().transform(name)
      val invokedParameters = CreateInvokedParameters().transform(name, method.parametersList)
      val invokedParametersList = CreateInvokedParametersList().transform(name, method.parametersList)
      val closures = CreateClosureCall().transform(method.parametersList)
      val closureProperties = closures.map { CreateClosureResultPropertyDeclaration().transform(name, it) }
      val closureCalls = closureProperties.zip(closures)
      val errorStub = CreateErrorStub().transform(name, method.throws)
      val returnStub = createReturnStub(method, name)
      addMethodProperties(method, invocationCheck, invocationCount, invokedParameters, invokedParametersList, closureProperties, errorStub, returnStub)
      addMethodDeclaration(method)
      addMethodAssignments(method, invocationCheck, invocationCount, invokedParameters, invokedParametersList, closureCalls, errorStub, returnStub)
      addClosingBrace()
    }
  }

  private fun createReturnStub(method: Method, name: String): PropertyDeclaration? {
    if (!method.returnType.originalType.isEmpty) {
      return CreateMethodReturnStub().transform(name, method.returnType.erasedType.text)
    }
    return null
  }

  private fun addMethodProperties(method: Method,
                                  invocationCheck: PropertyDeclaration,
                                  invocationCount: PropertyDeclaration,
                                  invokedParameters: TuplePropertyDeclaration?,
                                  invokedParametersList: TuplePropertyDeclaration?,
                                  closureProperties: List<PropertyDeclaration?>,
                                  errorStub: PropertyDeclaration,
                                  returnStub: PropertyDeclaration?) {
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCheck, "false"))
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCount, "0"))
    if (invokedParameters != null) addScopedLine(SwiftStringPropertyDeclaration().transform(invokedParameters) + "?")
    if (invokedParametersList != null) addScopedLine(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedParametersList))
    closureProperties.filterNotNull().forEach { addScopedLine(SwiftStringPropertyDeclaration().transform(it) + "?") }
    addScopedLine(SwiftStringPropertyDeclaration().transform(errorStub))
    addStubbedResult(returnStub, method)
  }

  private fun addStubbedResult(returnStub: PropertyDeclaration?, method: Method) {
    if (returnStub != null) {
      val defaultValue = DefaultValueStore().getDefaultValue(method.returnType.erasedType.text)
      addScopedLine(SwiftStringDefaultValuePropertyDeclaration().transform(returnStub, defaultValue))
    }
  }

  private fun addMethodDeclaration(method: Method) {
    addOverriddenLine(method.declarationText + " {")
  }

  private fun addMethodAssignments(method: Method,
                                   invocationCheck: PropertyDeclaration,
                                   invocationCount: PropertyDeclaration,
                                   invokedParameters: TuplePropertyDeclaration?,
                                   invokedParametersList: TuplePropertyDeclaration?,
                                   closureCalls: List<Pair<PropertyDeclaration?, Closure>>,
                                   errorStub: PropertyDeclaration,
                                   returnStub: PropertyDeclaration?) {
    addLine(SwiftStringPropertyAssignment().transform(invocationCheck, "true"))
    addLine(SwiftStringIncrementAssignment().transform(invocationCount))
    if (invokedParameters != null) addLine(SwiftStringPropertyAssignment().transform(invokedParameters, SwiftStringTupleForwardCall().transform(invokedParameters)))
    if (invokedParametersList != null) addLine(SwiftStringArrayAppender().transform(invokedParametersList, SwiftStringTupleForwardCall().transform(invokedParametersList)))
    closureCalls.forEach { addLine(SwiftStringClosureCall().transform(it.first?.name ?: "", it.second)) }
    addLine(SwiftStringThrowError().transform(errorStub))
    addReturnStub(returnStub, method)
  }

  private fun addReturnStub(returnStub: PropertyDeclaration?,
                            method: Method) {
    if (returnStub != null) {
      if (!method.returnType.isVoid && method.returnType.originalType.text != method.returnType.erasedType.text) {
        addLine(SwiftStringCastReturnProperty().transform(returnStub, method.returnType.originalType.text))
      } else {
        addLine(SwiftStringReturnProperty().transform(returnStub))
      }
    }
  }

  private fun addLine(line: String) {
    if (line.isEmpty()) {
      return
    }
    lines.add(line)
  }

  private fun addLine(scope: String, line: String) {
    when {
      line.isEmpty() -> return
      scope.isEmpty() -> addLine(line)
      else -> addLine(scope + " " + line)
    }
  }

  private fun addOverriddenLine(line: String) {
    if (override) {
      addScopedLine("override " + line)
    } else {
      addScopedLine(line)
    }
  }

  private fun addScopedLine(line: String) {
    addLine(scope, line)
  }

  private fun addInitialiserScopedLine(line: String) {
    if (scope == "open") {
      addLine("public", line)
    } else {
      addLine(scope, line)
    }
  }
}
