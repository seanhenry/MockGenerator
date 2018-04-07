package codes.seanhenry.mockgenerator.generator.templates

import codes.seanhenry.mockgenerator.entities.ClosureHelper.Companion.createClosure
import codes.seanhenry.mockgenerator.entities.Parameter
import codes.seanhenry.mockgenerator.ast.Method
import codes.seanhenry.mockgenerator.entities.Property
import codes.seanhenry.mockgenerator.generator.MockTransformer

class ScopeProtocolTest : MockGeneratorTestTemplate {

  override fun build(generator: MockTransformer) {
    generator.setScope("open")
    generator.add(
        Property("variable", "Object", true, "var variable: Object { get set }")
    )
    generator.add(
        Method.Builder("method")
            .parameter("param") { it.type("Object") }
            .parameter("closure") { it.type().function { } }
            .returnType("Object")
            .build()
    )
  }

  override fun getExpected(): String {
    return """
    open var invokedVariableSetter = false
    open var invokedVariableSetterCount = 0
    open var invokedVariable: Object?
    open var invokedVariableList = [Object]()
    open var invokedVariableGetter = false
    open var invokedVariableGetterCount = 0
    open var stubbedVariable: Object!
    open var variable: Object {
    set {
    invokedVariableSetter = true
    invokedVariableSetterCount += 1
    invokedVariable = newValue
    invokedVariableList.append(newValue)
    }
    get {
    invokedVariableGetter = true
    invokedVariableGetterCount += 1
    return stubbedVariable
    }
    }
    open var invokedMethod = false
    open var invokedMethodCount = 0
    open var invokedMethodParameters: (param: Object, Void)?
    open var invokedMethodParametersList = [(param: Object, Void)]()
    open var stubbedMethodResult: Object!
    open func method(param: Object, closure: () -> ()) -> Object {
    invokedMethod = true
    invokedMethodCount += 1
    invokedMethodParameters = (param, ())
    invokedMethodParametersList.append((param, ()))
    closure()
    return stubbedMethodResult
    }
      """.trimIndent()
  }
}
