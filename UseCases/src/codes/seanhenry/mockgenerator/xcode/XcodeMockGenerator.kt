package codes.seanhenry.mockgenerator.xcode

import codes.seanhenry.mockgenerator.entities.ProtocolMethod
import codes.seanhenry.mockgenerator.swift.BoolPropertyAssignmentToSwift
import codes.seanhenry.mockgenerator.swift.BoolPropertyDeclarationToSwift
import codes.seanhenry.mockgenerator.swift.IntPropertyDeclarationToSwift
import codes.seanhenry.mockgenerator.swift.IntPropertyIncrementAssignmentToSwift
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCheck
import codes.seanhenry.mockgenerator.usecases.CreateInvocationCount
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
      lines.add(BoolPropertyDeclarationToSwift().transform(invocationCheck))
      lines.add(IntPropertyDeclarationToSwift().transform(invocationCount))
      lines.add("func " + method.name + "() {")
      val invocationAssignment = CreateInvocationCheck(true).transform(method.name)
      lines.add(BoolPropertyAssignmentToSwift().transform(invocationAssignment))
      lines.add(IntPropertyIncrementAssignmentToSwift().transform(invocationCount))
      lines.add("}")
    }
    return lines.joinToString("\n")
  }

  fun add(method: ProtocolMethod) {
    methods.add(method)
  }
}
