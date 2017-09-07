package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.entities.ProtocolProperty
import codes.seanhenry.mockgenerator.entities.SwiftStringReturnProperty
import codes.seanhenry.mockgenerator.swift.*
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCheck
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCount
import codes.seanhenry.mockgenerator.usecases.CreateMethodReturnStub
import codes.seanhenry.mockgenerator.usecases.CreatePropertyGetterStub
import java.util.*

class XcodeMockGenerator {

  private val methods = ArrayList<ProtocolMethod>()
  private val properties = ArrayList<ProtocolProperty>()

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
      lines.add(BoolPropertyDeclarationToSwift().transform(setterInvocationCheck))
      lines.add(IntPropertyDeclarationToSwift().transform(setterInvocationCount))
      val getterName = property.name + "Getter"
      val getterInvocationCheck = CreateInvocationCheck(false).transform(getterName)
      val getterInvocationCount = CreateInvocationCount().transform(getterName)
      val returnStub = CreatePropertyGetterStub().transform(property.name, property.type)
      lines.add(BoolPropertyDeclarationToSwift().transform(getterInvocationCheck))
      lines.add(IntPropertyDeclarationToSwift().transform(getterInvocationCount))
      lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
      lines.add(property.getTrimmedSignature() + " {")
      lines.add("set {")
      lines.add(BoolPropertyAssignmentToSwift().transform(setterInvocationCheck, true))
      lines.add(IntPropertyIncrementAssignmentToSwift().transform(setterInvocationCount))
      lines.add("}")
      lines.add("get {")
      lines.add(BoolPropertyAssignmentToSwift().transform(getterInvocationCheck, true))
      lines.add(IntPropertyIncrementAssignmentToSwift().transform(getterInvocationCount))
      lines.add(SwiftStringReturnProperty().transform(returnStub))
      lines.add("}")
      lines.add("}")
    }
  }

  private fun appendMethodMocks(lines: ArrayList<String>) {
    for (method in methods) {
      val invocationCheck = CreateInvocationCheck(false).transform(method.name)
      val invocationCount = CreateInvocationCount().transform(method.name)
      var returnStub: PropertyDeclaration? = null
      if (method.returnType != null) {
        returnStub = CreateMethodReturnStub().transform(method.name, method.returnType)
      }
      lines.add(BoolPropertyDeclarationToSwift().transform(invocationCheck))
      lines.add(IntPropertyDeclarationToSwift().transform(invocationCount))
      if (returnStub != null) lines.add(SwiftStringPropertyDeclaration().transform(returnStub))
      lines.add(method.signature + " {")
      lines.add(BoolPropertyAssignmentToSwift().transform(invocationCheck, true))
      lines.add(IntPropertyIncrementAssignmentToSwift().transform(invocationCount))
      if (returnStub != null) lines.add(SwiftStringReturnProperty().transform(returnStub))
      lines.add("}")
    }
  }

  fun add(method: ProtocolMethod) {
    methods.add(method)
  }

  fun add(property: ProtocolProperty) {
    properties.add(property)
  }
}
