package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.*
import codes.seanhenry.mockgenerator.swift.SwiftStringReturnProperty
import codes.seanhenry.mockgenerator.swift.*
import codes.seanhenry.mockgenerator.usecases.*
import java.util.*

class XcodeMockGenerator {

  private val methods = ArrayList<ProtocolMethod>()
  private val properties = ArrayList<ProtocolProperty>()

  fun add(method: ProtocolMethod) {
    methods.add(method)
  }

  fun add(property: ProtocolProperty) {
    properties.add(property)
  }

  fun generate(): String {
    val lines = ArrayList<String>()
    appendPropertyMocks(lines)
    appendMethodMocks(lines)
    return lines.joinToString("\n")
  }

  private fun appendPropertyMocks(lines: ArrayList<String>) {
    for (property in properties) {
      val setterName = property.name + "Setter"
      val setterInvocationCheck = CreateInvocationCheck().transform(setterName)
      val setterInvocationCount = CreateInvocationCount().transform(setterName)
      val invokedProperty = CreateInvokedProperty().transform(property.name, TransformToOptional().transform(property.type))
      val invokedPropertyList = CreateInvokedPropertyList().transform(property.name, property.type)
      val getterName = property.name + "Getter"
      val getterInvocationCheck = CreateInvocationCheck().transform(getterName)
      val getterInvocationCount = CreateInvocationCount().transform(getterName)
      val returnStub = CreatePropertyGetterStub().transform(property.name, property.type)
      addSetterProperties(lines, setterInvocationCheck, setterInvocationCount, invokedProperty, invokedPropertyList, property.isWritable)
      addGetterProperties(lines, getterInvocationCheck, getterInvocationCount, returnStub)
      addPropertyDeclaration(lines, property)
      addSetterBlock(lines, setterInvocationCheck, setterInvocationCount, invokedProperty, invokedPropertyList, property.isWritable)
      addGetterBlock(lines, getterInvocationCheck, getterInvocationCount, returnStub, property.isWritable)
      addClosingBrace(lines)
    }
  }

  private fun addGetterBlock(lines: ArrayList<String>, getterInvocationCheck: PropertyDeclaration, getterInvocationCount: PropertyDeclaration, returnStub: PropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add("get {")
    }
    addPropertyInvocationCheckStatements(lines, getterInvocationCheck, getterInvocationCount)
    lines.add(SwiftStringReturnProperty().transform(returnStub))
    if (isWritable) {
      addClosingBrace(lines)
    }
  }

  private fun addSetterBlock(lines: ArrayList<String>, setterInvocationCheck: PropertyDeclaration, setterInvocationCount: PropertyDeclaration, invokedProperty: PropertyDeclaration, invokedPropertyList: PropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add("set {")
      addPropertyInvocationCheckStatements(lines, setterInvocationCheck, setterInvocationCount)
      addPropertyInvocationCaptureStatements(lines, invokedProperty, invokedPropertyList)
      addClosingBrace(lines)
    }
  }

  private fun addPropertyDeclaration(lines: ArrayList<String>, property: ProtocolProperty) {
    lines.add(property.getTrimmedSignature() + " {")
  }

  private fun addSetterProperties(lines: ArrayList<String>, setterInvocationCheck: PropertyDeclaration, setterInvocationCount: PropertyDeclaration, invokedProperty: PropertyDeclaration, invokedPropertyList: PropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(setterInvocationCheck, "false"))
      lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(setterInvocationCount, "0"))
      lines.add(SwiftStringPropertyDeclaration().transform(invokedProperty))
      lines.add(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedPropertyList))
    }
  }

  private fun addGetterProperties(lines: ArrayList<String>, getterInvocationCheck: PropertyDeclaration, getterInvocationCount: PropertyDeclaration, returnStub: PropertyDeclaration) {
    lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCheck, "false"))
    lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(getterInvocationCount, "0"))
    lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
  }

  private fun addClosingBrace(lines: ArrayList<String>) {
    lines.add("}")
  }

  private fun addPropertyInvocationCheckStatements(lines: ArrayList<String>, invocationCheck: PropertyDeclaration, invocationCount: PropertyDeclaration) {
    lines.add(SwiftStringPropertyAssignment().transform(invocationCheck, "true"))
    lines.add(SwiftStringIncrementAssignment().transform(invocationCount))
  }

  private fun addPropertyInvocationCaptureStatements(lines: ArrayList<String>, invokedProperty: PropertyDeclaration, invokedPropertyList: PropertyDeclaration) {
    lines.add(SwiftStringPropertyAssignment().transform(invokedProperty, "newValue"))
    lines.add(SwiftStringArrayAppender().transform(invokedPropertyList, "newValue"))
  }

  private fun appendMethodMocks(lines: ArrayList<String>) {
    for (method in methods) {
      val invocationCheck = CreateInvocationCheck().transform(method.name)
      val invocationCount = CreateInvocationCount().transform(method.name)
      val invokedParameters = CreateInvokedParameters().transform(method.name, method.parameters)
      val invokedParametersList = CreateInvokedParametersList().transform(method.name, method.parameters)
      val returnStub = createReturnStub(method)
      addMethodProperties(lines, invocationCheck, invocationCount, invokedParameters, invokedParametersList, returnStub)
      addMethodDeclaration(lines, method)
      addMethodAssignments(lines, invocationCheck, invocationCount, invokedParameters, invokedParametersList, returnStub)
      addClosingBrace(lines)
    }
  }

  private fun createReturnStub(method: ProtocolMethod): PropertyDeclaration? {
    if (method.returnType != null) {
      return CreateMethodReturnStub().transform(method.name, method.returnType)
    }
    return null
  }

  private fun addMethodProperties(lines: ArrayList<String>, invocationCheck: PropertyDeclaration, invocationCount: PropertyDeclaration, invokedParameters: TuplePropertyDeclaration?, invokedParametersList: TuplePropertyDeclaration?, returnStub: PropertyDeclaration?) {
    lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCheck, "false"))
    lines.add(SwiftStringImplicitValuePropertyDeclaration().transform(invocationCount, "0"))
    if (invokedParameters != null) lines.add(SwiftStringPropertyDeclaration().transform(invokedParameters) + "?")
    if (invokedParametersList != null) lines.add(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedParametersList))
    if (returnStub != null) lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
  }

  private fun addMethodDeclaration(lines: ArrayList<String>, method: ProtocolMethod) {
    lines.add(method.signature + " {")
  }

  private fun addMethodAssignments(lines: ArrayList<String>, invocationCheck: PropertyDeclaration, invocationCount: PropertyDeclaration, invokedParameters: TuplePropertyDeclaration?, invokedParametersList: TuplePropertyDeclaration?, returnStub: PropertyDeclaration?) {
    lines.add(SwiftStringPropertyAssignment().transform(invocationCheck, "true"))
    lines.add(SwiftStringIncrementAssignment().transform(invocationCount)) // TODO: change this and remove Int prop
    if (invokedParameters != null) lines.add(SwiftStringPropertyAssignment().transform(invokedParameters, SwiftStringTupleForwardCall().transform(invokedParameters)))
    if (invokedParametersList != null) lines.add(SwiftStringArrayAppender().transform(invokedParametersList, SwiftStringTupleForwardCall().transform(invokedParametersList)))
    if (returnStub != null) lines.add(SwiftStringReturnProperty().transform(returnStub))
  }
}
