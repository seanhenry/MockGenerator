package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.PropertyDeclaration
import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.entities.SwiftStringReturnProperty
import codes.seanhenry.mockgenerator.swift.*
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCheck
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCount
import codes.seanhenry.mockgenerator.usecases.CreateReturnStub
import java.util.*

class XcodeMockGenerator {

  private val methods = ArrayList<ProtocolMethod>()

  fun generate(): String {
    if (methods.isEmpty()) {
      return ""
    }
    val lines = ArrayList<String>()
    for (method in methods) {
      val invocationCheck = CreateInvocationCheck(false).transform(method.name)
      val invocationCount = CreateInvocationCount().transform(method.name)
      var returnStub: PropertyDeclaration? = null
      if (method.returnType != null) {
        returnStub = CreateReturnStub().transform(method.name, method.returnType)
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
    return lines.joinToString("\n")
  }

  fun add(method: ProtocolMethod) {
    methods.add(method)
  }
}
