package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringReturnProperty
import codes.seanhenry.mockgenerator.swift.*
import codes.seanhenry.mockgenerator.usecases.*
import codes.seanhenry.mockgenerator.util.DefaultValueStore
import codes.seanhenry.mockgenerator.util.MethodModel
import codes.seanhenry.mockgenerator.util.ParameterUtil
import codes.seanhenry.mockgenerator.util.UniqueMethodNameGenerator
import java.util.*

class XcodeMockGenerator {

  private val methods = ArrayList<ProtocolMethod>()
  private val properties = ArrayList<ProtocolProperty>()
  private var scope = ""
  private lateinit var nameGenerator: UniqueMethodNameGenerator
  private var lines = ArrayList<String>()

  fun setScope(scope: String) {
    this.scope = scope.trim() + " "
  }

  fun add(method: ProtocolMethod) {
    methods.add(method)
  }

  fun add(property: ProtocolProperty) {
    properties.add(property)
  }

  fun add(vararg methods: ProtocolMethod) {
    for (method in methods) {
      this.methods.add(method)
    }
  }

  fun add(vararg properties: ProtocolProperty) {
    for (property in properties) {
      this.properties.add(property)
    }
  }

  fun generate(): String {
    lines = ArrayList()
    generateOverloadedNames()
    appendPropertyMocks(properties)
    appendMethodMocks(methods)
    return lines.joinToString("\n")
  }

  private fun generateOverloadedNames() {
    val models = properties.map { toMethodModel(it) }.toMutableList()
    val others = methods.map { toMethodModel(it) }
    nameGenerator = UniqueMethodNameGenerator(models + others)
    nameGenerator.generateMethodNames()
  }

  private fun toMethodModel(method: ProtocolMethod): MethodModel {
    return MethodModel(method.name, method.parameterList)
  }

  private fun toMethodModel(property: ProtocolProperty): MethodModel {
    return MethodModel(property.name, "")
  }

  private fun appendPropertyMocks(properties: List<ProtocolProperty>) {
    for (property in properties) {
      val name = nameGenerator.getMethodName(toMethodModel(property).id)
      val setterName = name + "Setter"
      val setterInvocationCheck = CreateInvocationCheck().transform(setterName)
      val setterInvocationCount = CreateInvocationCount().transform(setterName)
      val invokedProperty = CreateInvokedProperty().transform(name, TransformToOptional().transform(property.type))
      val invokedPropertyList = CreateInvokedPropertyList().transform(name, property.type)
      val getterName = name + "Getter"
      val getterInvocationCheck = CreateInvocationCheck().transform(getterName)
      val getterInvocationCount = CreateInvocationCount().transform(getterName)
      val returnStub = CreatePropertyGetterStub().transform(name, property.type)
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

  private fun addPropertyDeclaration(property: ProtocolProperty) {
    addScopedLine(property.getTrimmedSignature() + " {")
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

  private fun addGetterProperties(property: ProtocolProperty,
                                  getterInvocationCheck: PropertyDeclaration,
                                  getterInvocationCount: PropertyDeclaration,
                                  returnStub: PropertyDeclaration) {
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCheck, "false"))
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCount, "0"))
    val defaultValue = DefaultValueStore().getDefaultValue(property.type)
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

  private fun appendMethodMocks(methods: List<ProtocolMethod>) {
    for (method in methods) {
      val name = nameGenerator.getMethodName(toMethodModel(method).id)
      val invocationCheck = CreateInvocationCheck().transform(name)
      val invocationCount = CreateInvocationCount().transform(name)
      val invokedParameters = CreateInvokedParameters().transform(name, method.parameterList)
      val invokedParametersList = CreateInvokedParametersList().transform(name, method.parameterList)
      val closures = CreateClosureCall().transform(ParameterUtil.getParameters(method.parameters))
      val closureProperties = closures.map { CreateClosureResultPropertyDeclaration().transform(name, it) }
      val closureCalls = closureProperties.zip(closures)
      val returnStub = createReturnStub(method, name)
      addMethodProperties(method, invocationCheck, invocationCount, invokedParameters, invokedParametersList, closureProperties, returnStub)
      addMethodDeclaration(method)
      addMethodAssignments(invocationCheck, invocationCount, invokedParameters, invokedParametersList, closureCalls, returnStub)
      addClosingBrace()
    }
  }

  private fun createReturnStub(method: ProtocolMethod, name: String): PropertyDeclaration? {
    if (method.returnType != null) {
      return CreateMethodReturnStub().transform(name, method.returnType)
    }
    return null
  }

  private fun addMethodProperties(method: ProtocolMethod,
                                  invocationCheck: PropertyDeclaration,
                                  invocationCount: PropertyDeclaration,
                                  invokedParameters: TuplePropertyDeclaration?,
                                  invokedParametersList: TuplePropertyDeclaration?,
                                  closureProperties: List<PropertyDeclaration?>,
                                  returnStub: PropertyDeclaration?) {
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCheck, "false"))
    addScopedLine(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCount, "0"))
    if (invokedParameters != null) addScopedLine(SwiftStringPropertyDeclaration().transform(invokedParameters) + "?")
    if (invokedParametersList != null) addScopedLine(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedParametersList))
    closureProperties.filterNotNull().forEach { addScopedLine(SwiftStringPropertyDeclaration().transform(it) + "?") }
    addStubbedResult(returnStub, method)
  }

  private fun addStubbedResult(returnStub: PropertyDeclaration?, method: ProtocolMethod) {
    if (returnStub != null) {
      val defaultValue = DefaultValueStore().getDefaultValue(method.returnType)
      addScopedLine(SwiftStringDefaultValuePropertyDeclaration().transform(returnStub, defaultValue))
    }
  }

  private fun addMethodDeclaration(method: ProtocolMethod) {
    addScopedLine(method.signature + " {")
  }

  private fun addMethodAssignments(invocationCheck: PropertyDeclaration,
                                   invocationCount: PropertyDeclaration,
                                   invokedParameters: TuplePropertyDeclaration?,
                                   invokedParametersList: TuplePropertyDeclaration?,
                                   closureCalls: List<Pair<PropertyDeclaration?, Closure>>,
                                   returnStub: PropertyDeclaration?) {
    addLine(SwiftStringPropertyAssignment().transform(invocationCheck, "true"))
    addLine(SwiftStringIncrementAssignment().transform(invocationCount))
    if (invokedParameters != null) addLine(SwiftStringPropertyAssignment().transform(invokedParameters, SwiftStringTupleForwardCall().transform(invokedParameters)))
    if (invokedParametersList != null) addLine(SwiftStringArrayAppender().transform(invokedParametersList, SwiftStringTupleForwardCall().transform(invokedParametersList)))
    closureCalls.forEach { addLine(SwiftStringClosureCall().transform(it.first?.name ?: "", it.second)) }
    if (returnStub != null) addLine(SwiftStringReturnProperty().transform(returnStub))
  }

  private fun addLine(line: String) {
    lines.add(line)
  }

  private fun addScopedLine(line: String) {
    addLine(scope + line)
  }
}
