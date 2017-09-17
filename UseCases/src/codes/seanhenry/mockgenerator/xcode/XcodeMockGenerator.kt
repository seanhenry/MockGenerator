package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.*
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
      val setterInvocationCheck = CreateInvocationCheck(false).transform(setterName)
      val setterInvocationCount = CreateInvocationCount().transform(setterName)
      val getterName = property.name + "Getter"
      val getterInvocationCheck = CreateInvocationCheck(false).transform(getterName)
      val getterInvocationCount = CreateInvocationCount().transform(getterName)
      val returnStub = CreatePropertyGetterStub().transform(property.name, property.type)
      addSetterProperties(lines, setterInvocationCheck, setterInvocationCount, property.isWritable)
      addGetterProperties(lines, getterInvocationCheck, getterInvocationCount, returnStub)
      addPropertyDeclaration(lines, property)
      addSetterBlock(lines, setterInvocationCheck, setterInvocationCount, property.isWritable)
      addGetterBlock(lines, getterInvocationCheck, getterInvocationCount, returnStub, property.isWritable)
      addClosingBrace(lines)
    }
  }

  private fun addGetterBlock(lines: ArrayList<String>, getterInvocationCheck: BoolPropertyDeclaration, getterInvocationCount: IntPropertyDeclaration, returnStub: PropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add("get {")
    }
    addPropertyInvocationStatements(lines, getterInvocationCheck, getterInvocationCount)
    lines.add(SwiftStringReturnProperty().transform(returnStub))
    if (isWritable) {
      addClosingBrace(lines)
    }
  }

  private fun addSetterBlock(lines: ArrayList<String>, setterInvocationCheck: BoolPropertyDeclaration, setterInvocationCount: IntPropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add("set {")
      addPropertyInvocationStatements(lines, setterInvocationCheck, setterInvocationCount)
      addClosingBrace(lines)
    }
  }

  private fun addPropertyDeclaration(lines: ArrayList<String>, property: ProtocolProperty) {
    lines.add(property.getTrimmedSignature() + " {")
  }

  private fun addSetterProperties(lines: ArrayList<String>, setterInvocationCheck: BoolPropertyDeclaration, setterInvocationCount: IntPropertyDeclaration, isWritable: Boolean) {
    if (isWritable) {
      lines.add(BoolPropertyDeclarationToSwift().transform(setterInvocationCheck))
      lines.add(IntPropertyDeclarationToSwift().transform(setterInvocationCount))
    }
  }

  private fun addGetterProperties(lines: ArrayList<String>, getterInvocationCheck: BoolPropertyDeclaration, getterInvocationCount: IntPropertyDeclaration, returnStub: PropertyDeclaration) {
    lines.add(BoolPropertyDeclarationToSwift().transform(getterInvocationCheck))
    lines.add(IntPropertyDeclarationToSwift().transform(getterInvocationCount))
    lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
  }

  private fun addClosingBrace(lines: ArrayList<String>) {
    lines.add("}")
  }

  private fun addPropertyInvocationStatements(lines: ArrayList<String>, invocationCheck: BoolPropertyDeclaration, invocationCount: IntPropertyDeclaration) {
    lines.add(BoolPropertyAssignmentToSwift().transform(invocationCheck, true))
    lines.add(IntPropertyIncrementAssignmentToSwift().transform(invocationCount))
  }

  private fun appendMethodMocks(lines: ArrayList<String>) {
    for (method in methods) {
      val invocationCheck = CreateInvocationCheck(false).transform(method.name)
      val invocationCount = CreateInvocationCount().transform(method.name)
      val invokedParameters = CreateInvokedParameters().transform(method.name, method.parameters)
      val invokedParametersList = CreateInvokedParametersList().transform(method.name, method.parameters)
      val returnStub = createReturnStub(method)
      addMethodProperties(lines, invocationCheck, invocationCount, invokedParameters, invokedParametersList, returnStub)
      addMethodDeclaration(lines, method)
      addMethodStatements(lines, invocationCheck, invocationCount, invokedParameters, invokedParametersList, returnStub)
      addClosingBrace(lines)
    }
  }

  private fun createReturnStub(method: ProtocolMethod): PropertyDeclaration? {
    if (method.returnType != null) {
      return CreateMethodReturnStub().transform(method.name, method.returnType)
    }
    return null
  }

  private fun addMethodProperties(lines: ArrayList<String>, invocationCheck: BoolPropertyDeclaration, invocationCount: IntPropertyDeclaration, invokedParameters: TuplePropertyDeclaration?, invokedParametersList: TuplePropertyDeclaration?, returnStub: PropertyDeclaration?) {
    lines.add(BoolPropertyDeclarationToSwift().transform(invocationCheck))
    lines.add(IntPropertyDeclarationToSwift().transform(invocationCount))
    if (invokedParameters != null) lines.add(SwiftStringPropertyDeclaration().transform(invokedParameters) + "?")
    if (invokedParametersList != null) lines.add(SwiftStringInitializedArrayPropertyDeclaration().transform(invokedParametersList))
    if (returnStub != null) lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
  }

  private fun addMethodDeclaration(lines: ArrayList<String>, method: ProtocolMethod) {
    lines.add(method.signature + " {")
  }

  private fun addMethodStatements(lines: ArrayList<String>, invocationCheck: BoolPropertyDeclaration, invocationCount: IntPropertyDeclaration, invokedParameters: TuplePropertyDeclaration?, invokedParametersList: TuplePropertyDeclaration?, returnStub: PropertyDeclaration?) {
    lines.add(BoolPropertyAssignmentToSwift().transform(invocationCheck, true)) // TODO: change this and remove bool prop
    lines.add(IntPropertyIncrementAssignmentToSwift().transform(invocationCount)) // TODO: change this and remove Int prop
    if (invokedParameters != null) lines.add(SwiftStringPropertyAssignment().transform(invokedParameters, SwiftStringTupleForwardCall().transform(invokedParameters)))
    if (invokedParametersList != null) lines.add(SwiftStringTupleArrayAppender().transform(invokedParametersList, SwiftStringTupleForwardCall().transform(invokedParametersList)))
    if (returnStub != null) lines.add(SwiftStringReturnProperty().transform(returnStub))
  }
}
